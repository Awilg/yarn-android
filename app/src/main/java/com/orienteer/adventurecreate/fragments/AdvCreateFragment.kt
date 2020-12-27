package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.orienteer.advcreateCard
import com.orienteer.adventurecreate.controller.AdvCreateEpoxyController
import com.orienteer.adventurecreate.viewmodel.AdvCreateViewModel
import com.orienteer.buttonFull
import com.orienteer.databinding.FragmentAdvcreateBinding
import com.orienteer.sectionTitle
import com.orienteer.util.TEST_ADV_CREATE_SUMMARY_COMPLETED
import com.orienteer.util.TEST_ADV_CREATE_SUMMARY_IN_PROGRESS

class AdvCreateFragment : Fragment() {

    private val viewModel : AdvCreateViewModel by viewModels()
    private lateinit var binding: FragmentAdvcreateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdvcreateBinding.inflate(inflater)

        // To use the binding with databinding you need to explicitly give the binding a reference to it.
        binding.viewmodel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inprogressRecyclerView.setItemSpacingDp(16)
        binding.inprogressRecyclerView.withModels {
            sectionTitle {
                id(hashCode())
                text("In progress")
            }

            TEST_ADV_CREATE_SUMMARY_IN_PROGRESS.forEach {
                advcreateCard {
                    id(hashCode())
                    advCreateSummary(it)
                }
            }

            buttonFull {
                id(hashCode())
                buttonText("Create new adventure")
                onClickListener { _ ->
                    findNavController().navigate(
                        AdvCreateFragmentDirections.actionAdvCreateFragmentToAdvCreateSummaryFragment()
                    )
                }
            }

            sectionTitle {
                id(hashCode())
                text("Completed")
            }

            TEST_ADV_CREATE_SUMMARY_COMPLETED.forEach {
                advcreateCard {
                    id(hashCode())
                    advCreateSummary(it)
                }
            }
        }
    }
}