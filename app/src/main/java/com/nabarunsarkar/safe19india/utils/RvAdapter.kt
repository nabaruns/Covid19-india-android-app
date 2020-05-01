package com.nabarunsarkar.safe19india.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nabarunsarkar.safe19india.R
import com.nabarunsarkar.safe19india.model.TotalDetails
import com.nabarunsarkar.safe19india.ui.main.DisplayDistrictActivity
import com.nabarunsarkar.safe19india.ui.main.EXTRA_MESSAGE
import com.nabarunsarkar.safe19india.ui.main.EXTRA_MESSAGE2
import kotlinx.coroutines.InternalCoroutinesApi


class RvAdapter(val userList: ArrayList<TotalDetails>, val intent_string: String) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    @InternalCoroutinesApi
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.list_row, p0, false)
        return ViewHolder(v).listen { pos, type ->
            val item = userList.get(pos)
            //TODO do other stuff here

            val message = item.state.toString()

            val intent = Intent(p0?.context, DisplayDistrictActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, intent_string)
                putExtra(EXTRA_MESSAGE2, message)
            }
            p0?.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return userList.size
    }
    override fun onBindViewHolder(p0: ViewHolder, position: Int) {

        val user = userList[position]

        p0.name?.text = userList[position].state
        p0.sub_title?.text = userList[position].statecode
        p0.date?.text = "Updated: "+userList[position].lastUpdatedTime.toString()
        p0.ButtonConfirm?.text = userList[position].confirmed
        p0.ButtonActive?.text = userList[position].active
        p0.ButtonDeath?.text = userList[position].deaths
        p0.ButtonRecovered?.text = userList[position].recovered
        p0.desc?.visibility = View.GONE
//        p0.rate?.rating = 3F

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.title)
        val sub_title = itemView.findViewById<TextView>(R.id.sub_title)
        val date = itemView.findViewById<TextView>(R.id.date_text)
        val desc = itemView.findViewById<TextView>(R.id.description)


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