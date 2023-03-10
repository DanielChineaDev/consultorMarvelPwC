package com.bpo.consultormarvelpwc

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.bpo.consultormarvelpwc.ApiConfig.MarvelAPIService
import com.bpo.consultormarvelpwc.ApiConfig.PersonajeAPI
import com.bpo.consultormarvelpwc.MainActivity.Companion.listaComics
import com.bpo.consultormarvelpwc.MainActivity.Companion.listaPersonajes
import com.bpo.consultormarvelpwc.MainActivity.Companion.listaSeries
import com.bpo.consultormarvelpwc.Models.Comic
import com.bpo.consultormarvelpwc.Models.Serie
import com.bpo.consultormarvelpwc.NavConfig.ItemsNav
import com.bpo.consultormarvelpwc.ui.theme.ConsultorMarvelPwCTheme
import com.bpo.consultormarvelpwc.NavConfig.ItemsNav.*
import com.bpo.consultormarvelpwc.NavConfig.NavigationHost
import com.bpo.consultormarvelpwc.NavConfig.ScreenPersonajes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

class MainActivity : ComponentActivity() {


    companion object {
        val listaPersonajes = mutableListOf<Personaje>()
        val listaComics = mutableListOf<Comic>()
        val listaSeries = arrayListOf<Serie>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        datosArray()

        setContent {
            ConsultorMarvelPwCTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ViewContainer()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
//Esta función recoge todos los datos y pinta la lista
fun MyGrid(
    contenidos: List<Contenido>,
    navController: NavHostController
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2), // 2 columnas en la cuadrícula
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.padding(bottom = 60.dp)
    ) {
        items(contenidos.size) { index ->
            val contenido = contenidos[index]
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        val thumbnailUri = Uri.encode(contenido.thumbnail)
                        navController.navigate(ScreenDetalles.route + "/${contenido.name}/${contenido.description}/${thumbnailUri}")
                    },
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp,
            ) {
                Column {
                    MyImageBig(contenido.thumbnail)
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        MyTexts(
                            title = contenido.name,
                            description = contenido.description
                        )
                    }
                }
            }
        }
    }
}

@Composable
//Título y descripción de cada card con su configuración de estilos, también limitamos las líneas de la descripción
fun MyTexts(title: String, description: String) {
    Column(
        modifier = Modifier
            .height(64.dp)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = description,
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.body2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
//Imagen con un placeholder para mostrar antes de que se carge la foto de la URL o por si no existe, además, la imagen se ajusta al espacio para no dejar huecos blancos
fun MyImageBig(imageUrl: String) {
    Image(
        painter = rememberImagePainter(
            data = imageUrl,
            builder = {
                crossfade(true)
                placeholder(R.mipmap.ic_placeholder_1)
            }
        ),
        contentDescription = "Foto que se usa de carátula para la lista",
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        contentScale = ContentScale.Crop,
    )
}

@Composable
//Controlador y llamada del bottom menu
fun ViewContainer() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    val navigationItem = listOf(
        ScreenPersonajes,
        ScreenComics,
        ScreenSeries
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { Toolbar() },
        bottomBar = { BottomNavigator(navController, navigationItem) }
    ) {
        NavigationHost(navController) {
            MyGrid(listaPersonajes, navController)
            ScreenPersonajes(navController)
        }
    }
}

@Composable
//Función que devuelve la ´ruta´ de la pantalla actual
fun currentRoute(navController: NavController): String? {
    val enter by navController.currentBackStackEntryAsState()
    return enter?.destination?.route
}

@Composable
//Función que le añade texto a la toolbar
fun Toolbar() {
    TopAppBar(
        title = { Text(text = "Consultor de marvel") },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
//Función que pinta el BottomMenu
fun BottomNavigator(
    navController: NavController,
    menuItems: List<ItemsNav>
) {
    BottomAppBar() {
        BottomNavigation(
        ) {
            val currentRoute = currentRoute(navController = navController)
            menuItems.forEach { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.route,
                    onClick = { navController.navigate(item.route) },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    alwaysShowLabel = false
                )
            }
        }
    }
}

fun datosArray(){
    listaPersonajes.add(
        Personaje(
            "Iron Man",
            "Genius billionaire playboy philanthropist",
            "https://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg"
        )
    )
    listaPersonajes.add(
        Personaje(
            "Spider-Man",
            "Friendly neighborhood web-slinger",
            "https://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg"
        )
    )
    listaPersonajes.add(
        Personaje(
            "Captain America",
            "Super soldier with a heart of gold",
            "https://i.annihil.us/u/prod/marvel/i/mg/3/50/537ba56d31087.jpg"
        )
    )
    listaPersonajes.add(
        Personaje(
            "Black Panther",
            "King of Wakanda and protector of the vibranium",
            "https://i.annihil.us/u/prod/marvel/i/mg/6/60/5261a80a67e7d.jpg"
        )
    )
    listaPersonajes.add(
        Personaje(
            "Hulk",
            "The strongest one there is",
            "https://i.annihil.us/u/prod/marvel/i/mg/5/a0/538615ca33ab0.jpg"
        )
    )
    listaPersonajes.add(
        Personaje(
            "Thor",
            "God of thunder and wielder of Mjolnir",
            "https://i.annihil.us/u/prod/marvel/i/mg/d/d0/5269657a74350.jpg"
        )
    )
    listaPersonajes.add(
        Personaje(
            "Doctor Strange",
            "Master of the mystic arts and protector of the multiverse",
            "https://i.annihil.us/u/prod/marvel/i/mg/5/f0/5261a85a501fe.jpg"
        )
    )
    listaPersonajes.add(
        Personaje(
            "Black Widow",
            "Russian spy turned Avenger",
            "https://i.annihil.us/u/prod/marvel/i/mg/f/30/50fecad1f395b.jpg"
        )
    )
    listaPersonajes.add(
        Personaje(
            "Ant-Man",
            "Shrinking hero with a heart of gold",
            "https://i.annihil.us/u/prod/marvel/i/mg/9/c0/53176aa9df48d.jpg"
        )
    )
    listaPersonajes.add(
        Personaje(
            "Wolverine",
            "The best there is at what he does",
            "https://i.annihil.us/u/prod/marvel/i/mg/9/00/537bcb1133fd7.jpg"
        )
    )

    listaComics.add(
        Comic(
            "Avengers: Endgame Prelude",
            "When a new threat emerges, Dr. Strange assembles a team of heroes to save the world from destruction.",
            "https://sportshub.cbsistatic.com/i/2021/04/09/fd293414-5c56-46c3-87a5-6f7b9be83401/best-thanos-comics-cover-1104415.jpg"
        )
    )

    listaComics.add(
        Comic(
            "Spider-Man: Far From Home Prelude",
            "Before Peter Parker returns in Spider-Man: Far From Home, catch up on his comic book adventures.",
            "https://d1466nnw0ex81e.cloudfront.net/n_iv/600/5410221.jpg"
        )
    )

    listaComics.add(
        Comic(
            "Marvel's Captain Marvel Prelude",
            "Prepare for Captain Marvel's cinematic debut with this all-new adventure written by Jim McCann, the writer of the New York Times Best-selling Marvel Zombies series!",
            "https://i0.wp.com/comicbookdispatch.com/wp-content/uploads/2022/10/capmarv2019043_preview_page_1.jpg?resize=720%2C1093&ssl=1"
        )
    )

    listaComics.add(
        Comic(
            "Avengers: Infinity War Prelude",
            "When a terrorist puts the Avengers at odds, CAPTAIN AMERICA and THE WINTER SOLDIER go rogue to find him - but IRON MAN isn't far behind.",
            "https://depor.com/resizer/2thr1yWYjuFGmtAydqaCAHaGsao=/1200x800/smart/filters:format(jpeg):quality(75)/cloudfront-us-east-1.images.arcpublishing.com/elcomercio/FLYZIFASUJC3VP4XWWUFOM4Q7E.jpg"
        )
    )

    listaComics.add(
        Comic(
            "Black Panther Prelude",
            "Wakanda. The most technologically advanced nation in the world - and protected by the mighty BLACK PANTHER! Learn how T'CHALLA became the legendary hero of Wakanda in this ALL-NEW, NEVER-BEFORE-TOLD tale set in the Marvel Cinematic Universe! See how the mantle was passed to the future king, in a time when super heroes were just emerging in the larger world, before this winter's BLACK PANTHER film hits theaters!",
            "https://cdn.vox-cdn.com/thumbor/3CqQACA_6lpfJqXCinaGDUJEqBI=/1400x1050/filters:format(jpeg)/cdn.vox-cdn.com/uploads/chorus_asset/file/6310321/black-panther.0.jpg"
        )
    )

    listaComics.add(
        Comic(
            "Spider-Man: Homecoming Prelude",
            "You adored his introduction in Marvel's Captain America: Civil War, you can't wait for his Hollywood Homecoming - now really get to know the Spider-Man of the Marvel Cinematic Universe in this prelude to his big-screen solo adventure! By fan-favorite scribe Robbie Thompson!",
            "https://pm1.narvii.com/6390/d58b0fc708b216e687e144a4e188b57e7a8633a9_hq.jpg"
        )
    )

    listaComics.add(
        Comic(
            "Doctor Strange Prelude",
            "Prepare for Marvel Studios' newest big-screen blockbuster by boning up with this essential collection of classics! First, relive Steve Ditko's classic debut of Doctor Strange from STRANGE TALES #110 and 111. Then, see how the Doctor was reintroduced in 1974 in MARVEL PREMIERE #3 and 4.",
            "https://media.comicbook.com/2016/07/doctor-strange-prelude-learned-189129.jpg"
        )
    )

    listaSeries.add(
        Serie(
            "WandaVision",
            "Wanda Maximoff and Vision - two super-powered beings living idealized suburban lives - begin to suspect that everything is not as it seems.",
            "https://cdn.milenio.com/uploads/media/2021/01/13/te-decimos-donde-ver-wandavision.jpg"
        )
    )
    listaSeries.add(
        Serie(
            "The Falcon and The Winter Soldier",
            "Following the events of Avengers: Endgame, Falcon (Anthony Mackie) and Winter Soldier (Sebastian Stan) team up in a global adventure that tests their abilities -- and their patience.",
            "https://cdn.mos.cms.futurecdn.net/7N5ibS2ZrcS9N282z49SJj.jpg"
        )
    )
    listaSeries.add(
        Serie(
            "Loki",
            "In Marvel Studios' \"Loki,\" the mercurial villain Loki (Tom Hiddleston) resumes his role as the God of Mischief in a new series that takes place after the events of \"Avengers: Endgame.\"",
            "https://i.blogs.es/78eb4a/cartel-loki-disney-plus/840_560.jpeg"
        )
    )
    listaSeries.add(
        Serie(
            "Daredevil",
            "A blind lawyer by day, vigilante by night. Matt Murdock fights the crime of New York as Daredevil.",
            "https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/styles/hc_1440x810/public/media/image/2022/03/daredevil-2648199.jpg?itok=MWqvv4j6"
        )
    )
    listaSeries.add(
        Serie(
            "Jessica Jones",
            "Following the tragic end of her brief superhero career, Jessica Jones tries to rebuild her life as a private investigator, dealing with cases involving people with remarkable abilities in New York City.",
            "https://as01.epimg.net/meristation/imagenes/2019/05/29/noticias/1559121480_928208_1559121717_noticia_normal.jpg"
        )
    )
    listaSeries.add(
        Serie(
            "Iron Fist",
            "Danny Rand resurfaces 15 years after being presumed dead. Now, with the power of the Iron Fist, he seeks to reclaim his past and fulfill his destiny.",
            "https://delagarde.nl/cache/i/6000/image/6192.w1024.1231d72.bc5ba47.q80.jpg"
        )
    )
    listaSeries.add(
        Serie(
            "Luke Cage",
            "When a sabotaged experiment gives him super strength and unbreakable skin, Luke Cage becomes a fugitive attempting to rebuild his life in Harlem and must soon confront his past and fight a battle for the heart of his city.",
            "https://sm.ign.com/ign_nl/news/m/marvels-lu/marvels-luke-cage-moeten-we-uitkijken-naar-de-serie_crbb.jpg"
        )
    )
    listaSeries.add(
        Serie(
            "The Punisher",
            "After the murder of his family, Marine veteran Frank Castle becomes the vigilante known as \"The Punisher,\" with only one goal in mind: to avenge them.",
            "https://i.blogs.es/4a06f6/cartel-punisher/1366_2000.jpg"
        )
    )
}