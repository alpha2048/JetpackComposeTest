package com.alpha2048.jetpackcomposetest.ui_component.component.parts

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.alpha2048.jetpackcomposetest.common.entity.OwnerEntity
import com.alpha2048.jetpackcomposetest.common.entity.RepositoryEntity

@Composable
fun RepositoryCard(
    repository: RepositoryEntity,
    onClick: (RepositoryEntity) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = {
                onClick(repository)
            })
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = rememberImagePainter(
                    data = repository.owner.avatarUrl,
                    builder = {
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = "",
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxHeight()
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
            ) {
                Text(
                    text = repository.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = repository.stargazersCount.toString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview("Home screen")
@Preview("Home screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Home screen (big font)", fontScale = 1.5f)
@Preview("Home screen (large screen)", device = Devices.PIXEL_C)
@Composable
fun PreviewRepositoryCard() {
    val entity = RepositoryEntity(
        id = 1,
        name = "test",
        htmlUrl = "test",
        stargazersCount = 1,
        owner = OwnerEntity(
            id = 1,
            avatarUrl = "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png"
        )
    )
    RepositoryCard(
        repository = entity,
        onClick = {},
    )
}
