package ray.kotlin.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ray.kotlin.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceLayout()
                }
            }
        }
    }
}

data class Artwork(
    val imageRes: Int,
    val title: String,
    val artist: String,
    val year: String
)


@Composable
fun ArtSpaceLayout() {
    val artworks by remember {
        mutableStateListOf(
            Artwork(
                R.drawable.manuel_meurisse_rlxu6diifbg_unsplash,
                "River Blake",
                "Blake Spear",
                "2003"
            ),
            Artwork(
                R.drawable.possessed_photography_jjgxjesmxoy_unsplash,
                "Robo",
                "Possessed Photography",
                "2015"
            ),
            Artwork(
                R.drawable.andrea_de_santis_zwd435_ewb4_unsplash,
                "A.I in real life",
                "Hi Tech Company",
                "2022"
            )
        )
    }

    var currentArt by remember { mutableStateOf(artworks.first()) }

    val currentIndex = artworks.indexOf(currentArt)
    val prevEnabler = currentIndex > 0
    val nextEnabler = currentIndex < artworks.size - 1
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        ElevatedCard(
            modifier = Modifier
                .padding(bottom = 30.dp)
                .width(280.dp)
                .height(380.dp),
            shape = RectangleShape,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary)
        ) {
            Image(
                painterResource(id = currentArt.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(30.dp)
            )
        }

        ArtTitle(
            title = currentArt.title,
            artist = currentArt.artist,
            year = currentArt.year
        )
    }
    Row(modifier = Modifier
        .padding(30.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom) {

        NavButton(
            label = "Previous",
            enabler = prevEnabler,
            onClickChange = { if (prevEnabler) currentArt = artworks[currentIndex - 1] }
        )
        NavButton(
            label = "Next",
            enabler = nextEnabler,
            onClickChange = { if (nextEnabler) currentArt = artworks[currentIndex + 1] }
        )

    }
}

@Composable
fun NavButton(label: String,enabler: Boolean = false, onClickChange: () -> Unit) {
    Button(
        onClick = onClickChange,
        modifier = Modifier
            .width(135.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onSecondary),
        elevation = ButtonDefaults.buttonElevation(1.dp),
        enabled = enabler){
        Text(text = label,
            color = Color.Black
        )

    }
}

@Composable
fun ArtTitle(title: String = "Artwork Title", artist: String="Artwork Artist", year: String ="2003") {
    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.onSecondary)
        .width(280.dp)
        .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Normal
        )
        BoldYearText(artist = artist, year = year)
    }
}

@Composable
fun BoldYearText(artist: String, year: String) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
            append(artist)
        }
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(" ($year)")
        }
    }
    Text(
        text = annotatedString,
        fontSize = 15.sp,
    )
}


@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        ArtSpaceLayout()
    }
}