package com.example.nom.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CrueltyFree
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nom.core.domain.models.SpiritEmotion
import com.example.nom.features.spirit.components.SpiritView
import com.example.nom.ui.components.NomButton
import com.example.nom.ui.components.NomGlowBackground
import com.example.nom.ui.components.NomOutlineButton
import com.example.nom.ui.components.NomTextButton
import com.example.nom.ui.components.NomTextField
import com.example.nom.ui.theme.NomDarkBg
import com.example.nom.ui.theme.NomGlassBorder
import com.example.nom.ui.theme.NomGlassFill
import com.example.nom.ui.theme.NomGreenAccent
import com.example.nom.ui.theme.NomTextSecondary

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NomDarkBg)
    ) {
        NomGlowBackground(modifier = Modifier.align(Alignment.TopCenter))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                NomTextButton(onClick = viewModel::onContinueWithoutAccount, text = "close")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Phone mockup
            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 160.dp, height = 240.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(NomGlassFill)
                        .border(2.dp, NomGlassBorder, RoundedCornerShape(32.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    SpiritView(emotion = SpiritEmotion.HAPPY, evolutionStage = 0)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Text section
            Text(
                text = "Ready to Grow?",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Join our community of plant enthusiasts and start your discovery journey.",
                style = MaterialTheme.typography.bodyLarge,
                color = NomTextSecondary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Form section
            NomTextField(
                value = email,
                onValueChange = { email = it },
                label = "EMAIL ADDRESS",
                leadingIcon = Icons.Outlined.Email
            )
            Spacer(modifier = Modifier.height(16.dp))
            NomTextField(
                value = password,
                onValueChange = { password = it },
                label = "PASSWORD",
                leadingIcon = Icons.Outlined.Lock,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(24.dp))
            
            // Social Auth
            NomOutlineButton(
                onClick = { /* Stub */ },
                text = "Continue with Google",
                icon = Icons.Outlined.CrueltyFree // Placeholder for 'G'
            )
            Spacer(modifier = Modifier.height(12.dp))
            NomOutlineButton(
                onClick = { /* Stub */ },
                text = "Continue with Apple",
                icon = Icons.Filled.Person
            )

            Spacer(modifier = Modifier.weight(1f))

            // Fine print
            val finePrint = buildAnnotatedString {
                append("By creating an account, you agree to our ")
                withStyle(style = SpanStyle(color = NomGreenAccent)) {
                    append("Terms of Service")
                }
                append(" and ")
                withStyle(style = SpanStyle(color = NomGreenAccent)) {
                    append("Privacy Policy")
                }
            }
            Text(
                text = finePrint,
                style = MaterialTheme.typography.labelSmall,
                color = NomTextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NomTextButton(onClick = viewModel::onContinueWithoutAccount, text = "Skip")
                NomButton(
                    onClick = { /* Stub */ },
                    text = "Create Account",
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
            }
        }
    }
}
