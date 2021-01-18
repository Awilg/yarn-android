package com.orienteer.explore.fragment

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.orienteer.*
import com.orienteer.databinding.FragmentAdvcreateReviewBinding
import com.orienteer.explore.viewmodel.ExploreViewModelV2
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.carousel
import com.orienteer.util.simpleController
import com.orienteer.util.withModelsFrom

class ExploreFragmentV2 : MavericksBaseFragment() {

    private val viewModel: ExploreViewModelV2 by activityViewModel()
    lateinit var binding: FragmentAdvcreateReviewBinding

    override fun epoxyController() = simpleController(viewModel) { state ->

        searchBar {
            id("search_bar")
        }

        state.featuredAdventure?.let { featuredAdventure ->
            exploreFeaturedCard {
                id("featuredCard")
                adventure(featuredAdventure)
                onClick { _ ->
                    findNavController().navigate(
                        ExploreFragmentV2Directions.actionExploreFragmentV2ToAdvDetailFragment(
                            featuredAdventure.id!!
                        )
                    )
                }
            }
        }

        callToCreate {
            id("callToCreate")
            onClick { _ ->
                findNavController().navigate(ExploreFragmentV2Directions.actionExploreFragmentV2ToCreatorTutorialFragment())
            }
        }

        state.nearbyAdventures?.let { nearbyAdventures ->
            sectionTitle {
                id("discoverLocal")
                text("Discover Local")
            }

            carousel {
                id("discoverLocalCarousel")
                numViewsToShowOnScreen(1.2f)

                withModelsFrom(nearbyAdventures) { adv ->
                    AdventureCardUnenclosedBindingModel_()
                        .id(adv.id)
                        .adventure(adv)
                        .onClick { _ ->
                            findNavController().navigate(
                                ExploreFragmentV2Directions.actionExploreFragmentV2ToAdvDetailFragment(
                                    adv.id!!
                                )
                            )
                        }
                }
            }


            buttonFull {
                id("discoverLocalButton")
                buttonText("Explore Nearby Adventures")
                onClickListener { _ ->
                    findNavController().navigate(
                        ExploreFragmentV2Directions.actionExploreFragmentV2ToMapFragment()
                    )
                }
            }
        }

        solveFromHome {
            id("solveFromHome")
            onClick { _ ->
                Toast.makeText(context, "clicked solve from home", Toast.LENGTH_SHORT).show()
            }
        }
    }
}