package com.example.mediaexplorer.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mediaexplorer.ContentSc
import com.example.mediaexplorer.R

@Composable
fun ContentCard(navController: NavHostController) {
    Card(
        onClick = {
//            navController.navigate(ContentSc(contentId = content.id, content.category.displayName))
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(R.drawable.otros),
                contentDescription = "content.name",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "content.name",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "content.information",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                //datos adicionales por tipo
//                when (content) {
//                    is Movies -> Text("Duración: ${content.duration} min", style = MaterialTheme.typography.bodySmall)
//                    is Series -> Text("Capítulos: ${content.cantCap}", style = MaterialTheme.typography.bodySmall)
//                    is Anime -> Text("Capítulos: ${content.cantCap}, Género: ${content.typeGender}", style = MaterialTheme.typography.bodySmall)
//                    is OtherContent -> Text("Tipo: ${content.typeContent}", style = MaterialTheme.typography.bodySmall)
//                }
            }
        }
    }
}