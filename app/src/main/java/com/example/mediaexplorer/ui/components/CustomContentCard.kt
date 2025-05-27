package com.example.mediaexplorer.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mediaexplorer.ContentSc
import com.example.mediaexplorer.R
import com.example.mediaexplorer.data.entity.Content

@Composable
fun ContentCard(
    navController: NavHostController,
    content: Content
)
 {
    Card(
        onClick = {
            navController.navigate(ContentSc(contentId = content.id, contentTitle = content.name))
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            if (content.contentImageUri != null) {
                AsyncImage(
                    model = Uri.parse(content.contentImageUri),
                    contentDescription = content.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.otros),
                    contentDescription = content.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            }


            Spacer(modifier = Modifier.width(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = content.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = content.information,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                when (content.typeContent) {
                    "Pelicula" -> Text("Duración: ${content.duration} min", style = MaterialTheme.typography.bodySmall)
                    "Series" -> Text("Capítulos: ${content.cantCap}", style = MaterialTheme.typography.bodySmall)
                    "Anime"-> Text("Capítulos: ${content.cantCap}, Género: ${content.typeGender}", style = MaterialTheme.typography.bodySmall)
                    else -> { Text("Tipo: ${content.typeContent}", style = MaterialTheme.typography.bodySmall)}
                }
                /*
                content.duration?.let {
                    Text("Duración: ${it} min", style = MaterialTheme.typography.bodySmall)
                }

                content.cantCap?.let {
                    Text("Capítulos: $it", style = MaterialTheme.typography.bodySmall)
                }

                content.typeGender?.let {
                    Text("Género: $it", style = MaterialTheme.typography.bodySmall)
                }

                content.typeContent?.let {
                    Text("Tipo: $it", style = MaterialTheme.typography.bodySmall)
                }
                */
            }
        }
    }
}
