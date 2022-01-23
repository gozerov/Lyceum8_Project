package ru.gozerov.lyceum8_project.screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.gozerov.core.fragment.BaseFragment
import ru.gozerov.lyceum8_project.R
import ru.gozerov.lyceum8_project.databinding.FragmentSelectableNewsBinding
import ru.gozerov.lyceum8_project.factory.SelectableNewsVMFactory
import ru.gozerov.lyceum8_project.singleton.appComponent
import ru.gozerov.lyceum8_project.viewmodel.SelectableNewsViewModel
import javax.inject.Inject

class SelectableNewsFragment : BaseFragment(R.layout.fragment_selectable_news) {

    private val args: SelectableNewsFragmentArgs by navArgs()

    override val viewModel by viewModels<SelectableNewsViewModel> {
        factory.create(args.selectableNewsId)
    }

    @Inject
    lateinit var factory: SelectableNewsVMFactory.Factory

    private lateinit var binding: FragmentSelectableNewsBinding

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectableNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.currentNewsFlow.collect { news ->
                binding.titleSelectNews.text = news.title
                binding.descriptionSelectNews.text = news.description
                launch {
                    binding.imageSelectNews.load(news.imageUrl) {
                        this
                            .scale(Scale.FIT)
                            .error(R.drawable.test_photo)
                            .dispatcher(Dispatchers.IO)
                    }
                }
            }
        }
    }

}