package fr.esgi.cocotton.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import fr.esgi.cocotton.R
import fr.esgi.cocotton.model.Icon

class SpinnerIconAdapter(val context: Context, var icons: List<Icon>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private var name: TextView? = null
    private var image: ImageView? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: inflater.inflate(R.layout.spinner_icon_item, parent, false)
        val icon: Icon = icons[position]

        name = view.findViewById(R.id.spinner_icon_item_text)
        image = view.findViewById(R.id.spinner_icon_item_image)

        name?.text = icon.name
        image?.setImageResource(icon.drawable)

        return view
    }

    override fun getItem(position: Int): Any? {
        return icons[position]
    }

    override fun getCount(): Int {
        return icons.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}