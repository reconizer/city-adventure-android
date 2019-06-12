package pl.reconizer.unfold.presentation.useradventures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_user_adventures.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.presentation.common.BaseFragment

class UserAdventuresFragment : BaseFragment(), IUserAdventuresView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_adventures, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener { navigator.goBack() }

    }

}