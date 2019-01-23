package com.ranjitzade.litehttp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.ranjitzade.litehttp.PinterestAdapter.PinterestVH
import com.ranjitzade.litehttp.lib.LiteHttp
import com.ranjitzade.litehttp.lib.widgets.LiteImageView
import com.ranjitzade.litehttp.models.Pinterest
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.String.format
import java.lang.String.valueOf
import java.util.*


/**
 * Created by ranjit
 */
class PinterestAdapter(private val mContext: Context) : RecyclerView.Adapter<PinterestVH>() {
    var pinterestList: List<Pinterest> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    val set = ConstraintSet()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinterestVH {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_pinterest, parent, false)
        return PinterestVH(view)
    }

    override fun onBindViewHolder(holder: PinterestVH, position: Int) {
        val pin = pinterestList[position]

        val posterRatio = format(Locale.getDefault(), "%d:%d", pin.width, pin.height)
        with(set) {
            clone(holder.constraintLayout)
            setDimensionRatio(holder.imageView.id, posterRatio)
            applyTo(holder.constraintLayout)
        }
        LiteHttp.imageLoader(mContext).url(pin.user?.profileImage?.medium!!).view(holder.imageViewUser).execute()
        holder.textViewUserName.text = pin.user?.name
        holder.textViewUserTitle.text = pin.user?.username

        val stateSet = intArrayOf(android.R.attr.state_checked * if (pin.likedByUser) 1 else -1)
        holder.imageViewLikes.setImageState(stateSet, true)
        holder.textViewLikes.text = valueOf(pin.likes)

        // load pinterest image
        val regular = pin.urls.regular
        if (regular != null) {
            holder.imageView.loadImage(regular, R.drawable.ic_image, R.drawable.ic_broken_image)

            // or we can also fetch that by http request
            /*LiteHttp.httpLoader()
                    .clazz(Bitmap.class)
                    .url(regular)
                    .method(Method.GET)
                    .listener(new IHttpListener<Bitmap>() {
                        @Override
                        public void onSuccess(Bitmap bitmap) {
                            Log.e(TAG, "position " + position);
                            holder.imageView.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onError(int code) {
                            Log.e(TAG, "position " + position + " code " + code);
                        }
                    }).execute();*/

        } else {
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_image))
        }
    }

    override fun onViewRecycled(holder: PinterestVH) {
        super.onViewRecycled(holder)
        holder.imageView.setImageDrawable(null)
        holder.textViewUserName.text = null
        holder.textViewUserTitle.text = null
    }

    override fun getItemCount(): Int {
        return pinterestList.size
    }

    inner class PinterestVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.imageViewSource)
        lateinit var imageView: LiteImageView

        @BindView(R.id.constraintLayout)
        lateinit var constraintLayout: ConstraintLayout

        @BindView(R.id.imageViewUser)
        lateinit var imageViewUser: CircleImageView

        @BindView(R.id.textViewUserTitle)
        lateinit var textViewUserTitle: AppCompatTextView

        @BindView(R.id.textViewUserName)
        lateinit var textViewUserName: AppCompatTextView

        @BindView(R.id.imageViewLikes)
        lateinit var imageViewLikes: AppCompatImageView

        @BindView(R.id.textViewLikes)
        lateinit var textViewLikes: AppCompatTextView

        init {
            ButterKnife.bind(this, itemView)
        }

        @OnClick(R.id.imageViewLikes, R.id.textViewLikes)
        fun onLikesClicked() {
            if (adapterPosition == -1) {
                return
            }

            val pin = pinterestList[adapterPosition]
            pin.likedByUser = !pin.likedByUser
            val stateSet = intArrayOf(android.R.attr.state_checked * if (pin.likedByUser) 1 else -1)
            imageViewLikes.setImageState(stateSet, true)
            // not maintained the state of likedByUser as it is not making any further calls or actions
        }
    }
}