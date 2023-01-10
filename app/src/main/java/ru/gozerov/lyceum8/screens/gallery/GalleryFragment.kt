package ru.gozerov.lyceum8.screens.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import ru.gozerov.lyceum8.R
import ru.gozerov.lyceum8.screens.gallery.adapter.galleryAdapter.GalleryAdapter
import ru.gozerov.lyceum8.utils.OnStatusBarUpdate
import ru.gozerov.lyceum8.utils.StatusBarStyle
import ru.gozerov.lyceum8.databinding.FragmentGalleryBinding

class GalleryFragment: Fragment() {

    private lateinit var binding: FragmentGalleryBinding

    private val imagesAdapter = GalleryAdapter()

    private var isToolbarVisible = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        (requireActivity() as OnStatusBarUpdate).update(StatusBarStyle.GALLERY)
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        isToolbarVisible = savedInstanceState?.getBoolean(KEY_IS_TOOLBAR_VISIBLE) ?: isToolbarVisible

        if (isToolbarVisible) {
            binding.galleryToolbar.visibility = View.VISIBLE
            binding.hideToolbarButton.setImageResource(R.drawable.ic_drop_up)
        } else {
            binding.galleryToolbar.visibility = View.GONE
            binding.hideToolbarButton.setImageResource(R.drawable.ic_drop_down)
        }

        binding.hideToolbarButton.setOnClickListener {
            if (isToolbarVisible) {
                binding.hideToolbarButton.setImageResource(R.drawable.ic_drop_down)
                binding.galleryToolbar.visibility = View.GONE
                binding.galleryToolbar.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.anim_slide_up))
                isToolbarVisible = false
            } else {
                binding.galleryToolbar.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.anim_slide_down))
                binding.galleryToolbar.visibility = View.VISIBLE
                binding.hideToolbarButton.setImageResource(R.drawable.ic_drop_up)
                isToolbarVisible = true
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val images = (arguments?.getStringArray(ARG_IMAGES) ?: throw Exception()).toList()

        imagesAdapter.data = images
        binding.newsImagesViewPager.adapter = imagesAdapter
        binding.newsImagesViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.txtCurrentNumOfPages.text = getString(R.string.current_page_is, position+1, images.size)
                super.onPageSelected(position)
            }
        })
        arguments?.let { arg ->
            val pageNum = arg.getInt(ARG_CURRENT_ITEM)
            binding.txtCurrentNumOfPages.text = getString(R.string.current_page_is, pageNum+1, images.size)
            binding.newsImagesViewPager.setCurrentItem(arg.getInt(ARG_CURRENT_ITEM), false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_IS_TOOLBAR_VISIBLE, isToolbarVisible)
    }


    companion object {
        const val ARG_IMAGES = "images"
        const val ARG_CURRENT_ITEM = "current_item"

        const val KEY_IS_TOOLBAR_VISIBLE = "is_toolbar_visible"
    }

}