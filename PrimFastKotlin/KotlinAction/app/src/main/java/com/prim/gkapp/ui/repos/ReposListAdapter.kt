package com.prim.gkapp.ui.repos

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.prim.gkapp.R
import com.prim.gkapp.config.Config
import com.prim.gkapp.data.model.Repository
import com.prim.gkapp.ext.loadImage
import com.prim.gkapp.ui.common.CommonListAdapter
import com.prim.lib_base.utils.*
import kotlinx.android.synthetic.main.item_repos_layout.view.*

/**
 * @desc
 * @author prim
 * @time 2019-06-19 - 07:06
 * @version 1.0.0
 */
class ReposListAdapter : CommonListAdapter<Repository>(R.layout.item_repos_layout, true) {
    override fun onBindItem(holder: RecyclerView.ViewHolder, item: Repository) {
        holder.itemView.apply {
            item_avatar.loadImage(item.owner.avatar_url, item.owner.login)
            item_title.text = item.name
            item_dec.text = item.description
            item.language.isNullOrEmpty().yes {
                item_language.text = "Unknown"
            }.otherwise {
                item_language.text = item.language
            }

            val res = Config.languageColor[item.language]

            res?.let { item_tips_language.setBackgroundResource(it) }

            item.fork.yes {
                item_fork.visibility = VISIBLE
                item_fork.text = "Forked form ${item.name}"
            }.otherwise {
                item_fork.visibility = GONE
            }

            (item.stargazers_count == 0).yes {
                ll_item_stargazers.visibility = GONE
            }.otherwise {
                ll_item_stargazers.visibility = VISIBLE
                item_stargazers.text = item.stargazers_count.toKilo()
            }

            (item.forks_count == 0).yes {
                ll_item_forked.visibility = GONE
            }.otherwise {
                ll_item_forked.visibility = VISIBLE
                item_forked_count.text = item.forks_count.toKilo()
            }

            (item.open_issues_count == 0).yes {
                ll_item_issues.visibility = GONE
            }.otherwise {
                ll_item_issues.visibility = VISIBLE
                item_issues_count.text = item.open_issues_count.toKilo()
            }

            //使用时间规则
            item_update_time.text = timeToData(item.updated_at).rule1()
        }
    }

    override fun onItemClick(view: View, item: Repository) {
    }

    override fun onItemLongClick(view: View, item: Repository) {

    }

    override fun onLoadErrorClick(view: View) {

    }
}