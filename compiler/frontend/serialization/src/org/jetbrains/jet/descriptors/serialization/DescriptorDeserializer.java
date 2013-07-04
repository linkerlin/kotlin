/*
 * Copyright 2010-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.descriptors.serialization;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jet.descriptors.serialization.descriptors.AnnotationDeserializer;
import org.jetbrains.jet.descriptors.serialization.descriptors.DeserializedTypeParameterDescriptor;
import org.jetbrains.jet.lang.descriptors.*;
import org.jetbrains.jet.lang.descriptors.Modality;
import org.jetbrains.jet.lang.descriptors.Visibility;
import org.jetbrains.jet.lang.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.jet.lang.descriptors.impl.*;
import org.jetbrains.jet.lang.resolve.DescriptorResolver;
import org.jetbrains.jet.lang.resolve.lazy.storage.LockBasedStorageManager;
import org.jetbrains.jet.lang.resolve.lazy.storage.StorageManager;
import org.jetbrains.jet.lang.types.Variance;

import java.util.*;

import static org.jetbrains.jet.descriptors.serialization.ProtoBuf.*;

public class DescriptorDeserializer {

    @NotNull
    public static DescriptorDeserializer create(
            @NotNull DeclarationDescriptor containingDeclaration,
            @NotNull NameResolver nameResolver,
            @NotNull ClassResolver classResolver,
            @NotNull AnnotationDeserializer annotationDeserializer
    ) {
        return new DescriptorDeserializer(new TypeDeserializer(null, nameResolver, classResolver, "Deserializer for " + containingDeclaration),
                                          containingDeclaration, nameResolver, annotationDeserializer);
    }

    @NotNull
    public static DescriptorDeserializer create(
            @NotNull TypeDeserializer typeDeserializer,
            @NotNull DeclarationDescriptor containingDeclaration,
            @NotNull NameResolver nameResolver,
            @NotNull AnnotationDeserializer annotationDeserializer
    ) {
        return new DescriptorDeserializer(typeDeserializer, containingDeclaration, nameResolver, annotationDeserializer);
    }

    private final DeclarationDescriptor containingDeclaration;
    private final NameResolver nameResolver;
    private final TypeDeserializer typeDeserializer;
    private final AnnotationDeserializer annotationDeserializer;

    private final StorageManager storageManager = new LockBasedStorageManager();

    private DescriptorDeserializer(
            @NotNull TypeDeserializer typeDeserializer,
            @NotNull DeclarationDescriptor containingDeclaration,
            @NotNull NameResolver nameResolver,
            @NotNull AnnotationDeserializer annotationDeserializer
    ) {
        this.typeDeserializer = typeDeserializer;
        this.containingDeclaration = containingDeclaration;
        this.nameResolver = nameResolver;
        this.annotationDeserializer = annotationDeserializer;
    }

    @NotNull
    public NameResolver getNameResolver() {
        return nameResolver;
    }

    @NotNull
    private DescriptorDeserializer createChildDeserializer(@NotNull DeclarationDescriptor descriptor) {
        return create(new TypeDeserializer(typeDeserializer, "Child deserializer for " + descriptor),
                      descriptor, nameResolver, annotationDeserializer);
    }

    @NotNull
    public CallableMemberDescriptor loadCallable(@NotNull Callable proto) {
        Callable.CallableKind callableKind = Flags.CALLABLE_KIND.get(proto.getFlags());
        switch (callableKind) {
            case FUN:
                return loadFunction(proto);
            case VAL:
            case VAR:
                return loadProperty(proto);
            case CONSTRUCTOR:
                return loadConstructor(proto);
        }
        throw new IllegalArgumentException("Unsupported callable kind: " + callableKind);
    }

    @NotNull
    private PropertyDescriptor loadProperty(@NotNull Callable proto) {
        int flags = proto.getFlags();
        PropertyDescriptorImpl property = new PropertyDescriptorImpl(
                containingDeclaration,
                getAnnotations(proto),
                modality(Flags.MODALITY.get(flags)),
                visibility(Flags.VISIBILITY.get(flags)),
                Flags.CALLABLE_KIND.get(flags) == Callable.CallableKind.VAR,
                nameResolver.getName(proto.getName()),
                memberKind(Flags.MEMBER_KIND.get(flags))
        );
        DescriptorDeserializer local = createChildDeserializer(property);
        List<TypeParameterDescriptor> typeParameters = local.typeParameters(proto.getTypeParametersList());
        property.setType(
                local.typeDeserializer.type(proto.getReturnType()),
                typeParameters,
                getExpectedThisObject(),
                local.typeDeserializer.typeOrNull(proto.hasReceiverType() ? proto.getReceiverType() : null)
        );

        PropertyGetterDescriptorImpl getter = null;
        PropertySetterDescriptorImpl setter = null;

        if (Flags.HAS_GETTER.get(flags)) {
            int getterFlags = proto.getGetterFlags();
            Boolean isNotDefault = proto.hasGetterFlags() || Flags.IS_NOT_DEFAULT.get(getterFlags);
            if (!isNotDefault) {
                getter = DescriptorResolver.createDefaultGetter(property);
            }
            else {
                getter = new PropertyGetterDescriptorImpl(
                        property, Collections.<AnnotationDescriptor>emptyList(),
                        modality(Flags.MODALITY.get(getterFlags)), visibility(Flags.VISIBILITY.get(getterFlags)),
                        isNotDefault, !isNotDefault, property.getKind()
                );
            }
            getter.initialize(property.getReturnType());
        }

        if (Flags.HAS_SETTER.get(flags)) {
            int setterFlags = proto.getSetterFlags();
            Boolean isNotDefault = proto.hasSetterFlags() || Flags.IS_NOT_DEFAULT.get(setterFlags);
            if (!isNotDefault) {
                setter = DescriptorResolver.createDefaultSetter(property);
            }
            else {
                setter = new PropertySetterDescriptorImpl(
                        property, getSetterAnnotations(proto, setterFlags),
                        modality(Flags.MODALITY.get(setterFlags)), visibility(Flags.VISIBILITY.get(setterFlags)),
                        isNotDefault, !isNotDefault, property.getKind()
                );
                setter.initializeDefault();
            }
        }

        property.initialize(getter, setter);

        return property;
    }

    @NotNull
    private CallableMemberDescriptor loadFunction(@NotNull Callable proto) {
        int flags = proto.getFlags();
        SimpleFunctionDescriptorImpl function = new SimpleFunctionDescriptorImpl(
                containingDeclaration,
                getAnnotations(proto),
                nameResolver.getName(proto.getName()),
                memberKind(Flags.MEMBER_KIND.get(flags))
        );
        DescriptorDeserializer local = createChildDeserializer(function);
        List<TypeParameterDescriptor> typeParameters = local.typeParameters(proto.getTypeParametersList());
        function.initialize(
                local.typeDeserializer.typeOrNull(proto.hasReceiverType() ? proto.getReceiverType() : null),
                getExpectedThisObject(),
                typeParameters,
                local.valueParameters(proto.getValueParametersList()),
                local.typeDeserializer.type(proto.getReturnType()),
                modality(Flags.MODALITY.get(flags)),
                visibility(Flags.VISIBILITY.get(flags)),
                Flags.INLINE.get(flags)

        );
        return function;
    }

    @Nullable
    private ReceiverParameterDescriptor getExpectedThisObject() {
        return containingDeclaration instanceof ClassDescriptor
               ? ((ClassDescriptor) containingDeclaration).getThisAsReceiverParameter() : null;
    }

    @NotNull
    private CallableMemberDescriptor loadConstructor(@NotNull Callable proto) {
        ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
        ConstructorDescriptorImpl descriptor = new ConstructorDescriptorImpl(
                classDescriptor,
                getAnnotations(proto),
                // TODO: primary
                true);
        DescriptorDeserializer local = createChildDeserializer(descriptor);
        descriptor.initialize(
                classDescriptor.getTypeConstructor().getParameters(),
                local.valueParameters(proto.getValueParametersList()),
                visibility(Flags.VISIBILITY.get(proto.getFlags())),
                !classDescriptor.isInner()
        );
        descriptor.setReturnType(local.typeDeserializer.type(proto.getReturnType()));
        return descriptor;
    }

    private List<AnnotationDescriptor> getAnnotations(Callable proto) {
        return Flags.HAS_ANNOTATIONS.get(proto.getFlags())
               ? annotationDeserializer.loadCallableAnnotations(proto)
               : Collections.<AnnotationDescriptor>emptyList();
    }

    private List<AnnotationDescriptor> getSetterAnnotations(Callable proto, int setterFlags) {
        return Flags.HAS_ANNOTATIONS.get(setterFlags) ? annotationDeserializer.loadSetterAnnotations(proto) : Collections
                .<AnnotationDescriptor>emptyList();
    }

    private static CallableMemberDescriptor.Kind memberKind(Callable.MemberKind memberKind) {
        switch (memberKind) {
            case DECLARATION:
                return CallableMemberDescriptor.Kind.DECLARATION;
            case FAKE_OVERRIDE:
                return CallableMemberDescriptor.Kind.FAKE_OVERRIDE;
            case DELEGATION:
                return CallableMemberDescriptor.Kind.DELEGATION;
            case SYNTHESIZED:
                return CallableMemberDescriptor.Kind.SYNTHESIZED;
        }
        throw new IllegalArgumentException("Unknown member kind: " + memberKind);
    }

    @NotNull
    public static Modality modality(@NotNull ProtoBuf.Modality modality) {
        switch (modality) {
            case FINAL:
                return Modality.FINAL;
            case OPEN:
                return Modality.OPEN;
            case ABSTRACT:
                return Modality.ABSTRACT;
        }
        throw new IllegalArgumentException("Unknown modality: " + modality);
    }

    @NotNull
    public static Visibility visibility(@NotNull ProtoBuf.Visibility visibility) {
        switch (visibility) {
            case INTERNAL:
                return Visibilities.INTERNAL;
            case PRIVATE:
                return Visibilities.PRIVATE;
            case PROTECTED:
                return Visibilities.PROTECTED;
            case PUBLIC:
                return Visibilities.PUBLIC;
            case EXTRA:
                throw new UnsupportedOperationException("Extra visibilities are not supported yet"); // TODO
        }
        throw new IllegalArgumentException("Unknown visibility: " + visibility);
    }

    @NotNull
    public static ClassKind classKind(@NotNull ProtoBuf.Class.Kind kind) {
        switch (kind) {
            case CLASS:
                return ClassKind.CLASS;
            case TRAIT:
                return ClassKind.TRAIT;
            case ENUM_CLASS:
                return ClassKind.ENUM_CLASS;
            case ENUM_ENTRY:
                return ClassKind.ENUM_ENTRY;
            case ANNOTATION_CLASS:
                return ClassKind.ANNOTATION_CLASS;
            case OBJECT:
                return ClassKind.OBJECT;
            case CLASS_OBJECT:
                return ClassKind.CLASS_OBJECT;
        }
        throw new IllegalArgumentException("Unknown class kind: " + kind);
    }

    @NotNull
    public List<TypeParameterDescriptor> typeParameters(@NotNull List<TypeParameter> protos) {
        List<TypeParameterDescriptor> result = new ArrayList<TypeParameterDescriptor>(protos.size());
        for (int i = 0; i < protos.size(); i++) {
            TypeParameter proto = protos.get(i);
            DeserializedTypeParameterDescriptor descriptor = typeParameter(proto, i);
            result.add(descriptor);
        }
        return result;
    }

    @NotNull
    private DeserializedTypeParameterDescriptor typeParameter(TypeParameter proto, int index) {
        int id = proto.getId();
        DeserializedTypeParameterDescriptor descriptor = new DeserializedTypeParameterDescriptor(
                storageManager,
                typeDeserializer,
                proto,
                containingDeclaration,
                nameResolver.getName(proto.getName()),
                variance(proto.getVariance()),
                proto.getReified(),
                index
        );
        typeDeserializer.registerTypeParameter(id, descriptor);
        return descriptor;
    }

    private static Variance variance(TypeParameter.Variance proto) {
        switch (proto) {
            case IN:
                return Variance.IN_VARIANCE;
            case OUT:
                return Variance.OUT_VARIANCE;
            case INV:
                return Variance.INVARIANT;
        }
        throw new IllegalStateException("Unknown projection: " + proto);
    }

    @NotNull
    private List<ValueParameterDescriptor> valueParameters(@NotNull List<Callable.ValueParameter> protos) {
        List<ValueParameterDescriptor> result = new ArrayList<ValueParameterDescriptor>(protos.size());
        for (int i = 0; i < protos.size(); i++) {
            Callable.ValueParameter proto = protos.get(i);
            result.add(valueParameter(proto, i));
        }
        return result;
    }

    private ValueParameterDescriptor valueParameter(Callable.ValueParameter proto, int index) {
        return new ValueParameterDescriptorImpl(
                containingDeclaration,
                index,
                getAnnotations(proto),
                nameResolver.getName(proto.getName()),
                typeDeserializer.type(proto.getType()),
                Flags.DECLARES_DEFAULT_VALUE.get(proto.getFlags()),
                typeDeserializer.typeOrNull(proto.hasVarargElementType() ? proto.getVarargElementType() : null));
    }

    private List<AnnotationDescriptor> getAnnotations(Callable.ValueParameter proto) {
        return Flags.HAS_ANNOTATIONS.get(proto.getFlags())
               ? annotationDeserializer.loadValueParameterAnnotations(proto)
               : Collections.<AnnotationDescriptor>emptyList();
    }
}
