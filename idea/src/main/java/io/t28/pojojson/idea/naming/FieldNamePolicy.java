package io.t28.pojojson.idea.naming;

import com.google.common.base.CaseFormat;
import com.google.inject.Inject;
import com.intellij.psi.PsiNameHelper;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.codeStyle.VariableKind;
import com.squareup.javapoet.TypeName;
import io.t28.json2java.core.naming.DefaultNamePolicy;
import io.t28.json2java.core.naming.NamePolicy;

import javax.annotation.Nonnull;

public class FieldNamePolicy implements NamePolicy {
    private static final String PREFIX = "_";

    private final PsiNameHelper nameHelper;
    private final JavaCodeStyleManager codeStyleManager;

    @Inject
    public FieldNamePolicy(@Nonnull PsiNameHelper nameHelper, @Nonnull JavaCodeStyleManager codeStyleManager) {
        this.nameHelper = nameHelper;
        this.codeStyleManager = codeStyleManager;
    }

    @Nonnull
    @Override
    public String convert(@Nonnull String name, @Nonnull TypeName type) {
        final String propertyName = DefaultNamePolicy.format(name, CaseFormat.LOWER_CAMEL);
        final String fieldName = codeStyleManager.propertyNameToVariableName(propertyName, VariableKind.FIELD);
        if (nameHelper.isKeyword(fieldName)) {
            return PREFIX + fieldName;
        }
        return fieldName;
    }

}