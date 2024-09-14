package com.otsembo.portfolio.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    label: @Composable (() -> Unit),
    fieldType: AppTextFieldType = AppTextFieldType.Regular,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    if (fieldType == AppTextFieldType.TextArea) {
        modifier.height(100.dp)
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier =
            modifier.height(
                when (fieldType) {
                    AppTextFieldType.TextArea -> 150.dp
                    else -> TextFieldDefaults.MinHeight + 8.dp
                },
            ),
        singleLine = singleLine,
        label = label,
        shape = RoundedCornerShape(50),
        visualTransformation =
            when (fieldType) {
                AppTextFieldType.Password -> PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
        minLines =
            when (fieldType) {
                AppTextFieldType.TextArea -> 5
                else -> 1
            },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
    )
}

enum class AppTextFieldType {
    Regular,
    Password,
    TextArea,
}
