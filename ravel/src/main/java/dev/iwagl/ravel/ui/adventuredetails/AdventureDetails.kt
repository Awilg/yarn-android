package dev.iwagl.ravel.ui.adventuredetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.google.accompanist.insets.*
import dev.iwagl.ravel.R
import dev.iwagl.ravel.ui.discover.*

@Composable
fun AdventureDetails(
    navigateUp: () -> Unit
) {
    AdventureDetails()
}

@Composable
internal fun AdventureDetails() {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        ) {
            item { DetailsPhotoGallery() }
            item { DetailsSummary() }
            item { CallForCreation() }
            item { CallForCreation() }
        }

}


@Composable
@Preview
internal fun DetailsPhotoGallery() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.seattle_kraken_secondary_logo),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(200.dp).fillMaxWidth()
        )
        Row(modifier = Modifier.align(Alignment.TopEnd)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_star_24), contentDescription = "",
                modifier = Modifier.zIndex(1.0f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_star_24), contentDescription = "",
                modifier = Modifier.zIndex(1.0f)
            )
        }
    }
}

@Composable
@Preview
internal fun DetailsSummary() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(top = 12.dp, bottom = 12.dp, start = 24.dp, end = 24.dp)
        ) {

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.hunt_the_kraken),
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_star_24), contentDescription = "",
                    modifier = Modifier.zIndex(1.0f)
                )
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically ,modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.hunt_the_kraken),
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
                Image(
                    painter = painterResource(id = R.drawable.seattle_kraken_secondary_logo),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(45.dp).width(45.dp).fillMaxWidth().clip(RoundedCornerShape(percent = 50))
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.lorem_ipsum),
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}