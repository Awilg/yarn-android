package com.orienteer.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.maps.model.LatLng
import com.orienteer.models.*
import java.time.LocalDateTime
import java.util.*

// Seattle LatLng
var testHunts: List<Adventure> = listOf(
	Adventure(
        "dummy",
        "A romp around South Lake Union",
        "Come see for yourself where the Amazonians live! Beware!",
        LatLng(47.6062, -122.3321),
        listOf(
            Clue(
                "clue_1",
                "This is a text clue",
                "This is a hint",
                ClueType.Text,
                LatLng(47.539, -122.305),
                ClueState.ACTIVE,
                "answer"
            )
        ),
		"https://cdn.geekwire.com/wp-content/uploads/2016/04/20160423_Amazon_biodomes_73-630x421.jpg",
		listOf(
			"https://cdn.geekwire.com/wp-content/uploads/2016/04/20160423_Amazon_biodomes_73-630x421.jpg",
			"https://www.tripsavvy.com/thmb/PjesAcSOetIKsjSdINiJBW5kKgU=/4161x2341/smart/filters:no_upscale()/view-of-columbia-tower-142567995-5b10b2d68e1b6e0036cc5956.jpg",
			"https://www.visitseattle.org/wp-content/uploads/2015/03/VS_OVG_WS2019_Waterfront_credit-Shutterstock_web.jpg"
		)
    ),
	Adventure(
		"id_1", "Treasure Hunt 1", "This is a description!", LatLng(47.6062, -122.3321), listOf(
        Clue("clue_1", "This is a location clue", "This is a hint", ClueType.Location, LatLng(47.539, -122.305), ClueState.ACTIVE),
        Clue("clue_2", "This is the photo clue", "Photo hint", ClueType.Photo, LatLng(47.539, -122.305),ClueState.LOCKED),
        Clue(
            "clue_3",
            "This is the text clue",
            "This is a hint",
            ClueType.Text,
            LatLng(47.539, -122.305),
            ClueState.LOCKED
        )
    ),
		"https://www.tripsavvy.com/thmb/PjesAcSOetIKsjSdINiJBW5kKgU=/4161x2341/smart/filters:no_upscale()/view-of-columbia-tower-142567995-5b10b2d68e1b6e0036cc5956.jpg",
		listOf(
			"https://cdn.geekwire.com/wp-content/uploads/2016/04/20160423_Amazon_biodomes_73-630x421.jpg",
			"https://www.tripsavvy.com/thmb/PjesAcSOetIKsjSdINiJBW5kKgU=/4161x2341/smart/filters:no_upscale()/view-of-columbia-tower-142567995-5b10b2d68e1b6e0036cc5956.jpg",
			"https://www.visitseattle.org/wp-content/uploads/2015/03/VS_OVG_WS2019_Waterfront_credit-Shutterstock_web.jpg"
		)
    ),
	Adventure(
		"id_2",
		"Treasure Hunt 2",
		"This is a really description. I want to see what happens to the layout " +
            "when the description is this long. Nanananananananananananananan BATMAN!", LatLng(47.82, -122.320), listOf(
        Clue("clue_3", "This is the text clue", "This is a hint", ClueType.Text, LatLng(47.539, -122.305),ClueState.LOCKED),
        Clue("clue_3", "This is the text clue", "This is a hint", ClueType.Text, LatLng(47.539, -122.305),ClueState.LOCKED),
        Clue(
            "clue_3",
            "This is the text clue",
            "This is a hint",
            ClueType.Text,
            LatLng(47.539, -122.305),
            ClueState.LOCKED
        )
    ),
		"https://www.visitseattle.org/wp-content/uploads/2015/03/VS_OVG_WS2019_Waterfront_credit-Shutterstock_web.jpg",
		listOf(
			"https://cdn.geekwire.com/wp-content/uploads/2016/04/20160423_Amazon_biodomes_73-630x421.jpg",
			"https://www.tripsavvy.com/thmb/PjesAcSOetIKsjSdINiJBW5kKgU=/4161x2341/smart/filters:no_upscale()/view-of-columbia-tower-142567995-5b10b2d68e1b6e0036cc5956.jpg",
			"https://www.visitseattle.org/wp-content/uploads/2015/03/VS_OVG_WS2019_Waterfront_credit-Shutterstock_web.jpg"
		)
    ),
	Adventure(
		"id_3", "Treasure Hunt 3", "This is a description!", LatLng(48.0, -122.370), listOf(
        Clue(
            "clue_1",
            "This is a photo clue",
            "This is a hint",
            ClueType.Photo,
            LatLng(47.539, -122.305),
            ClueState.ACTIVE
        )
    ),
		"https://cdn.shopify.com/s/files/1/0265/9793/files/479-WhidbeyCoffee273580-HDR_grande.jpg",
		listOf(
			"https://cdn.geekwire.com/wp-content/uploads/2016/04/20160423_Amazon_biodomes_73-630x421.jpg",
			"https://www.tripsavvy.com/thmb/PjesAcSOetIKsjSdINiJBW5kKgU=/4161x2341/smart/filters:no_upscale()/view-of-columbia-tower-142567995-5b10b2d68e1b6e0036cc5956.jpg",
			"https://www.visitseattle.org/wp-content/uploads/2015/03/VS_OVG_WS2019_Waterfront_credit-Shutterstock_web.jpg"
		)
    ),
	Adventure(
        "id_4", "Treasure Hunt 4", "This is a description!", LatLng(46.9, -122.399), emptyList(),
        "https://crosscut.com/sites/default/files/styles/three_two_960x640/public/images/articles/DSC_4964_e2it6j.jpg"
    ),
	Adventure(
		"id_5",
		"Treasure Hunt 5",
		"This is a description!",
		LatLng(47.2062, -122.3412),
        emptyList(),
        "https://cdn.vox-cdn.com/thumbor/VjrdnIjF9QTw5qjGR1KKFfi1EPg=/0x0:2000x1329/1200x900/filters:focal(654x260:974x580)/cdn.vox-cdn.com/uploads/chorus_image/image/55235639/eatersea0916_pike_place_market_shutterstock_mcarter.0.0.jpg"
	),
	Adventure(
		"id_6",
		"Treasure Hunt 6",
		"This is a description!",
		LatLng(47.309, -122.3662),
        emptyList(),
        "https://www.seattle.gov/images/Departments/ParksAndRecreation/Parks/GHI/GasWorksPark3.jpg"
	),
	Adventure(
		"id_7",
		"Hunt d'Elizabeth de Ville Beacon",
		"This is a really description. I want to see what happens to the layout when the description is this long. Nanananananananananananananan BATMAN!",
		LatLng(47.536, -122.300),
		listOf(
        Clue("clue_1", "This is the first clue!", "This is a hint", ClueType.Location, LatLng(47.539, -122.305),ClueState.ACTIVE),
        Clue("clue_2", "This is the second clue", "Photo hint", ClueType.Photo, LatLng(47.539, -122.305),ClueState.LOCKED),
        Clue("clue_3", "This is the third clue", "This is a hint", ClueType.Text, LatLng(47.539, -122.305),ClueState.LOCKED),
        Clue("clue_4", "This is the fourth clue", "This is a hint", ClueType.Location, LatLng(47.539, -122.305),ClueState.LOCKED),
        Clue("clue_5", "This is the fifth clue", "This is a hint", ClueType.Location, LatLng(47.539, -122.305),ClueState.LOCKED),
        Clue("clue_6", "This is the sixth clue", "This is a hint", ClueType.Location, LatLng(47.539, -122.305),ClueState.LOCKED),
        Clue("clue_7", "This is the seventh clue", "This is a hint", ClueType.Location, LatLng(47.539, -122.305),ClueState.LOCKED),
        Clue("clue_8", "This is the eighth clue", "This is a hint", ClueType.Location, LatLng(47.539, -122.305),ClueState.LOCKED),
        Clue("clue_9", "This is the ninth clue", "This is a hint", ClueType.Location, LatLng(47.539, -122.305),ClueState.LOCKED))),
	Adventure(
		"id_8",
		"Treasure Hunt 8",
		"This is a description!",
		LatLng(48.6062, -122.803),
		emptyList()
	),
	Adventure(
		"id_9",
		"Treasure Hunt 9",
		"This is a description!",
		LatLng(48.022, -122.555),
		emptyList()
	),
	Adventure(
		"id_10",
		"Treasure Hunt 10",
		"This is a description!",
		LatLng(48.11, -122.222),
		emptyList()
	),
	Adventure(
		"id_11",
		"Treasure Hunt 11",
		"This is a description!",
		LatLng(49.02, -122.3321),
		emptyList()
	),
	Adventure(
		"id_12",
		"Treasure Hunt 12",
		"This is a description!",
		LatLng(46.992, -122.444),
		emptyList()
	),
	Adventure(
		"id_13",
		"Treasure Hunt 13",
		"This is a description!",
		LatLng(46.682, -123.0),
		emptyList()
	),
	Adventure(
		"id_14",
		"Treasure Hunt 14",
		"This is a description!",
		LatLng(47.442, -121.999),
		emptyList()
	),
	Adventure(
		"id_15",
		"James's Hunt #1",
		"This is a description!",
		LatLng(47.552, -121.89),
		emptyList()
	),
	Adventure(
		"id_16",
		"Treasure Hunt 16",
		"This is a description!",
		LatLng(47.6242, -121.75),
		emptyList()
	),
	Adventure(
		"id_17",
		"Treasure Hunt 17",
		"This is a description!",
		LatLng(47.6102, -123.111),
		emptyList()
	),
	Adventure(
		"id_18",
		"Treasure Hunt 18",
		"This is a description!",
		LatLng(47.69, -122.89),
		emptyList()
	),
	Adventure(
		"id_19",
		"Pog pog pog",
		"This is a description!",
		LatLng(47.77, -122.82),
		emptyList()
	),
	Adventure(
		"id_20",
		"Treasure Hunt 20",
		"This is a description!",
		LatLng(47.721, -122.420),
		emptyList()
	)
)

var testWfhHunts: List<Adventure> = listOf(
	Adventure(
		"dummy",
		"A romp around South Lake Union",
		"Come see for yourself where the Amazonians live! Beware!",
		LatLng(47.6062, -122.3321),
		listOf(
			Clue(
				"clue_1",
				"This is a text clue",
				"This is a hint",
				ClueType.Text,
				LatLng(47.539, -122.305),
				ClueState.ACTIVE,
				"answer"
			)
		),
		"https://cdn.geekwire.com/wp-content/uploads/2016/04/20160423_Amazon_biodomes_73-630x421.jpg",
		listOf(
			"https://cdn.geekwire.com/wp-content/uploads/2016/04/20160423_Amazon_biodomes_73-630x421.jpg",
			"https://www.tripsavvy.com/thmb/PjesAcSOetIKsjSdINiJBW5kKgU=/4161x2341/smart/filters:no_upscale()/view-of-columbia-tower-142567995-5b10b2d68e1b6e0036cc5956.jpg",
			"https://www.visitseattle.org/wp-content/uploads/2015/03/VS_OVG_WS2019_Waterfront_credit-Shutterstock_web.jpg"
		)
	),
	Adventure(
		"id_1", "Treasure Hunt 1", "This is a description!", LatLng(47.6062, -122.3321), listOf(
			Clue(
				"clue_3",
				"This is the text clue",
				"This is a hint",
				ClueType.Text,
				LatLng(47.539, -122.305),
				ClueState.LOCKED
			)
		),
		"https://www.tripsavvy.com/thmb/PjesAcSOetIKsjSdINiJBW5kKgU=/4161x2341/smart/filters:no_upscale()/view-of-columbia-tower-142567995-5b10b2d68e1b6e0036cc5956.jpg",
		listOf(
			"https://cdn.geekwire.com/wp-content/uploads/2016/04/20160423_Amazon_biodomes_73-630x421.jpg",
			"https://www.tripsavvy.com/thmb/PjesAcSOetIKsjSdINiJBW5kKgU=/4161x2341/smart/filters:no_upscale()/view-of-columbia-tower-142567995-5b10b2d68e1b6e0036cc5956.jpg",
			"https://www.visitseattle.org/wp-content/uploads/2015/03/VS_OVG_WS2019_Waterfront_credit-Shutterstock_web.jpg"
		)
	),
	Adventure(
		"id_2",
		"Treasure Hunt 2",
		"This is a really description. I want to see what happens to the layout " +
				"when the description is this long. Nanananananananananananananan BATMAN!", LatLng(47.82, -122.320), listOf(
			Clue("clue_3", "This is the text clue", "This is a hint", ClueType.Text, LatLng(47.539, -122.305),ClueState.LOCKED),
			Clue("clue_3", "This is the text clue", "This is a hint", ClueType.Text, LatLng(47.539, -122.305),ClueState.LOCKED),
			Clue(
				"clue_3",
				"This is the text clue",
				"This is a hint",
				ClueType.Text,
				LatLng(47.539, -122.305),
				ClueState.LOCKED
			)
		),
		"https://www.visitseattle.org/wp-content/uploads/2015/03/VS_OVG_WS2019_Waterfront_credit-Shutterstock_web.jpg",
		listOf(
			"https://cdn.geekwire.com/wp-content/uploads/2016/04/20160423_Amazon_biodomes_73-630x421.jpg",
			"https://www.tripsavvy.com/thmb/PjesAcSOetIKsjSdINiJBW5kKgU=/4161x2341/smart/filters:no_upscale()/view-of-columbia-tower-142567995-5b10b2d68e1b6e0036cc5956.jpg",
			"https://www.visitseattle.org/wp-content/uploads/2015/03/VS_OVG_WS2019_Waterfront_credit-Shutterstock_web.jpg"
		)
	))

var testFeaturedHunt =
	Adventure(
		"kraken",
		"Hunt The Kraken",
		"There are whispers of its whereabouts...",
		LatLng(47.6062, -122.3321),
		listOf(
			Clue(
				"clue_1",
				"This is a text clue",
				"This is a hint",
				ClueType.Text,
				LatLng(47.539, -122.305),
				ClueState.ACTIVE,
				"answer"
			)
		),
		"https://i.redd.it/d2ifvz972nc51.png",
		listOf(
			"https://i.redd.it/d2ifvz972nc51.png",
			"https://i0.wp.com/russianmachineneverbreaks.com/wp-content/uploads/2020/07/seattle-kraken-jerseys.png",
			"https://www.terracestandard.com/wp-content/uploads/2020/07/22215638_web1_seattle-kraken.jpg"
		)
	)


@RequiresApi(Build.VERSION_CODES.O)
var TEST_ADVENTURES_IN_PROGRESS : List<AdventureInProgress> = listOf(
    AdventureInProgress("AdventureInProgress_1", "adventureId_1", "clue_1", LocalDateTime.now(), LocalDateTime.now(), false),
    AdventureInProgress("AdventureInProgress_2", "adventureId_2", "clue_2", LocalDateTime.now(), LocalDateTime.now(), false),
    AdventureInProgress("AdventureInProgress_3", "adventureId_3", "clue_3", LocalDateTime.now(), LocalDateTime.now(), false)

)

var TEST_USER : User = User("id", "JimmyTheKid", listOf("id_15", "id_19"), emptyList(), "https://firebasestorage.googleapis.com/v0/b/orienteer-dev.appspot.com/o/IMG_20190702_100344.jpg?alt=media&token=5537d40d-e6d4-4b77-a30e-8c2d0bb11d04", Calendar.getInstance().time)
