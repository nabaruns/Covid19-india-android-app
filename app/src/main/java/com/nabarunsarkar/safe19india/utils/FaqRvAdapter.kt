package com.nabarunsarkar.safe19india.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nabarunsarkar.safe19india.R
import com.nabarunsarkar.safe19india.model.FaqResponse
import kotlinx.coroutines.InternalCoroutinesApi

class FaqRvAdapter(
    val userList: List<FaqResponse.Faq>
) : RecyclerView.Adapter<FaqRvAdapter.ViewHolder>() {

    @InternalCoroutinesApi
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.list_row, p0, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, position: Int) {

        val user = userList[position]

//        p0.name?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
        p0.name?.text = userList[position].question
        p0.sub_title?.text = userList[position].link
        p0.desc?.text = userList[position].answer
        p0.date?.visibility = View.GONE
        p0.sub_title?.setTextIsSelectable(true)
        p0.ButtonConfirm?.visibility = View.GONE
        p0.imageArrow?.visibility = View.GONE
        p0.ButtonActive?.visibility = View.GONE
        p0.ButtonRecovered?.visibility = View.GONE
        p0.ButtonDeath?.visibility = View.GONE
//        p0.rate?.rating = 3F


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.title)
        val sub_title = itemView.findViewById<TextView>(R.id.sub_title)
        val desc = itemView.findViewById<TextView>(R.id.description)
        val date = itemView.findViewById<TextView>(R.id.date_text)
        val imageArrow = itemView.findViewById<ImageView>(R.id.imageView)


        val ButtonConfirm = itemView.findViewById<Button>(R.id.ButtonConfirm)
        val ButtonActive = itemView.findViewById<Button>(R.id.ButtonActive)
        val ButtonRecovered = itemView.findViewById<Button>(R.id.ButtonRecovered)
        val ButtonDeath = itemView.findViewById<Button>(R.id.ButtonDeath)
//        val rate = itemView.findViewById<RatingBar>(R.id.rating_bar)

    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(getAdapterPosition(), getItemViewType())
        }
        return this
    }
}