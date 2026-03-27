package com.example.nom.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nom.ui.theme.NomGlassBorder
import com.example.nom.ui.theme.NomGlassFill
import com.example.nom.ui.theme.NomTheme

@Composable
fun NomCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = NomGlassFill),
        border = BorderStroke(1.dp, NomGlassBorder)
    ) {
        Column(modifier = Modifier.padding(20.dp), content = content)
    }
}

@Preview
@Composable
fun NomCardPreview() {
    NomTheme {
        NomCard {}
    }
}
