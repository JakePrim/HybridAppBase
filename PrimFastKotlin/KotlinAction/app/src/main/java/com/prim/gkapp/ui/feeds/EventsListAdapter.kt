package com.prim.gkapp.ui.feeds

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.prim.gkapp.R
import com.prim.gkapp.config.Config
import com.prim.gkapp.data.model.Events
import com.prim.gkapp.ext.loadImage
import com.prim.gkapp.ui.common.CommonListAdapter
import com.prim.lib_base.utils.*
import kotlinx.android.synthetic.main.include_item_bottom.view.*
import kotlinx.android.synthetic.main.item_events_layout.view.*

/**
 * @desc
 * @author prim
 * @time 2019-07-02 - 20:34
 * @version 1.0.0
 */
class EventsListAdapter : CommonListAdapter<Events>(R.layout.item_events_layout, true) {
    override fun onBindItem(holder: RecyclerView.ViewHolder, item: Events) {
        holder.itemView.apply {
            item_avatar.loadImage(item.actor.avatarUrl, item.actor.login)
            item_name.text = item.actor.login
            item_events_repo_name.text = item.repo.name
            when (item.type) {
                EventType.CREATE_EVENT.value -> {
                    item_action.text = "created"
                    if (item.payload.description.isEmpty()) {
                        item_events_repo_dec.visibility = GONE
                    } else {
                        item_events_repo_dec.visibility = VISIBLE
                        item_events_repo_dec.text = item.payload.description
                    }
                }
                EventType.FORK_EVENT.value -> {
                    item_action.text = "forked"
                    item.payload.forkee.apply {
                        if (this.description.isEmpty()) {
                            item_events_repo_dec.visibility = GONE
                        } else {
                            item_events_repo_dec.visibility = VISIBLE
                            item_events_repo_dec.text = item.payload.description
                        }
                        ll_item_language.visibility = VISIBLE
                        this.language.isEmpty().yes {
                            item_language.text = "Unknown"
                        }.otherwise {
                            item_language.text = this.language
                        }
                        val res = Config.languageColor[this.language]
                        res?.let { item_tips_language.setBackgroundResource(it) }

                        (this.stargazers_count == 0).yes {
                            ll_item_stargazers.visibility = GONE
                        }.otherwise {
                            ll_item_stargazers.visibility = VISIBLE
                            item_stargazers.text = this.stargazers_count.toKilo()
                        }

                        (this.forks_count == 0).yes {
                            ll_item_forked.visibility = GONE
                        }.otherwise {
                            ll_item_forked.visibility = VISIBLE
                            item_forked_count.text = this.forks_count.toKilo()
                        }

                        (this.open_issues_count == 0).yes {
                            ll_item_issues.visibility = GONE
                        }.otherwise {
                            ll_item_issues.visibility = VISIBLE
                            item_issues_count.text = this.open_issues_count.toKilo()
                        }
                    }
                }
                EventType.WATCH_EVENT.value -> {
                    item_action.text = "starred"
                }
                else -> {
                    item_action.text = "unkonwn"
                }
            }

            item.createdAt?.let {
                item_time.text = timeToData(it).rule1()
            }

        }
    }

    override fun onItemClick(view: View, item: Events) {
    }

    override fun onItemLongClick(view: View, item: Events) {

    }
}