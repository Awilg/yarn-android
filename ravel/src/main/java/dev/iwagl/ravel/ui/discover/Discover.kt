package dev.iwagl.ravel.ui.discover

import android.content.*
import android.widget.*
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
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.google.accompanist.insets.*
import dev.iwagl.ravel.ui.common.*


@Composable
internal fun Discover(openAdventure: (adventureId: Long) -> Unit) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        item { FeaturedCard(openAdventure) }
        item { FeaturedSponsoredCard() }
        item { CallForCreation() }
        item { SectionTitle("Discover Local") }
        item { CallForCreation() }
        item { CallForCreation() }
        item { CallForCreation() }
    }
}

fun log(context: Context, str: String) {
    Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
}

@Composable
internal fun FeaturedCard(openAdventure: (adventureId: Long) -> Unit) {
    val context = LocalContext.current

    FeaturedCard(
        title = "Fear the Kraken",
        subtitle = "There are whispers of its whereabouts",
        openAdventure = openAdventure)
}

// TODO : hoist states out
@Composable
internal fun FeaturedCard(
    title: String,
    subtitle: String,
    openAdventure: (adventureId: Long) -> Unit
) {
    val context = LocalContext.current
    Box(modifier = Modifier
        .padding(start = 24.dp, end = 24.dp)
        .clickable {
            log(context = context, "TEST")
            openAdventure(123)
        }) {
        Image(
            painter = painterResource(id = R.drawable.seattle_kraken_secondary_logo),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(percent = 5))
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_star_24), contentDescription = "",
            modifier = Modifier
                .align(
                    Alignment.TopEnd
                )
                .zIndex(1.0f)
        )
        Row(modifier = Modifier.align(Alignment.BottomStart)) {
            Column(modifier = Modifier.padding(start = 12.dp, bottom = 18.dp)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
@Preview
internal fun discoverCard() {
    Column {
        Box(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
            Image(
                painter = painterResource(id = R.drawable.seattle_kraken_secondary_logo),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(percent = 5))
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_favorite_border_24),
                contentDescription = "",
                modifier = Modifier
                    .align(
                        Alignment.TopEnd
                    )
                    .zIndex(1.0f)
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .width(200.dp)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Some local adventure title",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Some local subtext",
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Composable
@Preview
internal fun FeaturedSponsoredCard() {
    Box(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
        Image(
            painter = painterResource(id = R.drawable.buck), contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(percent = 10))
        )
        Row(modifier = Modifier.align(Alignment.CenterStart)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .width(200.dp)
                    .padding(8.dp)
            ) {
                Text(
                    text = "The 'Buck's Beginnings",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Delve into the caffeinated origins of the coffee giant",
                    color = Color.White,
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Composable
@Preview
internal fun SearchBar() {
    Box(modifier = Modifier.background(color = Color.Cyan)) {
        Image(
            painter = painterResource(id = R.drawable.seattle_kraken_secondary_logo),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
                .clip(RoundedCornerShape(percent = 5))
        )
    }
}


@Composable
@Preview
internal fun CallForCreation() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(top = 12.dp, bottom = 12.dp, start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = stringResource(R.string.design_the_next_adventure),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.call_to_create_description),
                color = Color.Black,
                fontSize = 14.sp,
            )
            Text(
                text = stringResource(R.string.learn_more),
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

