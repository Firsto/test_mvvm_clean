package studio.inprogress.yetanothertestapi.ui.items

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mikepenz.fastadapter.items.AbstractItem
import studio.inprogress.yetanothertestapi.R
import studio.inprogress.yetanothertestapi.databinding.ItemGithubUserBinding
import studio.inprogress.yetanothertestapi.domain.model.UserEntity
import studio.inprogress.yetanothertestapi.utils.load

class UserItem(private val user: UserEntity) : AbstractItem<UserItem.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<ItemGithubUserBinding>(itemView)
    }

    override fun bindView(holder: ViewHolder, payloads: MutableList<Any>) {
        super.bindView(holder, payloads)
        holder.binding?.username?.text = user.login
        holder.binding?.url?.text = user.html_url
        holder.binding?.userAvatar?.load(
            user.avatar_url,
            CircleCrop()
        )
    }

    override val layoutRes: Int = R.layout.item_github_user
    override val type: Int = R.id.item_user

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }
}