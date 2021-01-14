package com.orienteer.explore.fragment

import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.orienteer.*
import com.orienteer.adventurecreate.fragments.AdvCreateFragmentDirections
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

        state.featuredAdventure?.let {
            exploreFeaturedCard {
                id("featuredCard")
                adventure(it)
            }
        }

        callToCreate {
            id("callToCreate")
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
                }
            }


            buttonFull {
                id("discoverLocalButton")
                buttonText("Explore Nearby Adventures")
                onClickListener { _ ->
                    findNavController().navigate(
                        AdvCreateFragmentDirections.actionAdvCreateFragmentToAdvCreateSummaryFragment()
                    )
                }
            }
        }
    }
}