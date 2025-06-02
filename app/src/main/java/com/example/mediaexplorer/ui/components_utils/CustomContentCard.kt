package com.example.mediaexplorer.ui.components_utils

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
            navController.navigate(ContentSc(content.id, content.categoryName)) {
                launchSingleTop = true
                restoreState = true
            }
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
                        .align(Alignment.CenterVertically)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.placeholder),
                    contentDescription = content.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .align(Alignment.CenterVertically)
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
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = content.information,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                when (content.categoryName) {
                    "Película" -> {
                        Text(
                            stringResource(R.string.duration),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            "${content.duration} min",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    "Serie" -> {
                        Text(
                            stringResource(R.string.chapters),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            "${content.cantCap}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    "Anime"-> {
                        Row {
                            Text(
                                stringResource(R.string.chapters),
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                "${content.cantCap}, ",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                stringResource(R.string.genero),
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                "${content.typeGender}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    else -> {
                        Text(
                            stringResource(R.string.type),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text("${content.categoryName}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
