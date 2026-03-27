package com.example.nom.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomGlassBorder
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTextPrimary
import com.example.nom.ui.theme.NomTheme

@Composable
fun NomButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = NomGreenAccent,
            contentColor = NomDarkBg,
            disabledContainerColor = NomGreenAccent.copy(alpha = 0.3f),
            disabledContentColor = NomDarkBg.copy(alpha = 0.5f)
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(text, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun NomOutlineButton(
    onClick: () -> Unit, 
    text: String, 
    modifier: Modifier = Modifier, 
    icon: ImageVector? = null
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, NomGlassBorder),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = NomTextPrimary)
    ) {
        if (icon != null) {
            Icon(icon, null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}

@Preview
@Composable
fun NomButtonPreview() {
    NomTheme {
        NomButton(onClick = {}, text = "Nom Button")
    }
}

@Preview
@Composable
fun NomOutlineButtonPreview() {
    NomTheme {
        NomOutlineButton(onClick = {}, text = "Nom Outline Button")
    }
}
