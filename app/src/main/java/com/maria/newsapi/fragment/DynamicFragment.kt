package com.maria.newsapi.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.maria.newsapi.R
import com.maria.newsapi.WebViewActivity
import com.maria.newsapi.model.KotlinMainResponse

class DynamicFragment : Fragment() {
    var num = 0
    var title: TextView? = null
    var description: TextView? = null
    var publishedAt: TextView? = null
    var content: TextView? = null
    var button: Button? = null
    var dataresponse: KotlinMainResponse? = null
    lateinit var image: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        avedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dynamic, container, false)

        num = requireArguments().getInt("someInt", 0)
        dataresponse = requireArguments().getSerializable("data") as KotlinMainResponse?
        image = view.findViewById(R.id.image)
        title = view.findViewById(R.id.title)
        description = view.findViewById(R.id.description)
        publishedAt = view.findViewById(R.id.publishedAt)
        content = view.findViewById(R.id.content)
        button = view.findViewById(R.id.webview)



        Glide.with(requireContext()).load(dataresponse!!.data?.get(num)!!.urlToImage)
            .thumbnail(0.5f)
            .placeholder(R.drawable.loading)
            .error(R.drawable.image_not_available)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(image)

        title!!.text = dataresponse?.data!!.get(num).title
        description!!.text = dataresponse?.data!!.get(num).description
        publishedAt!!.text = dataresponse?.data!!.get(num).publishedAt

        button?.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", dataresponse!!.data!!.get(num).url)
            startActivity(intent)
        }

        return view;
    }

    companion object {
        fun addfrag(num: Int, data: KotlinMainResponse?): DynamicFragment {
            val fragment = DynamicFragment()
            val args = Bundle()
            args.clear();
            args.putInt("someInt", num)
            args.putSerializable("data", data);
            fragment.arguments = args
            return fragment
        }
    }
}