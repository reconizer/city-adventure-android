package pl.reconizer.cityadventure.presentation.map.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_game_map.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import pl.reconizer.cityadventure.presentation.map.IMapView

class GameMapFragment : BaseFragment() {

    private val mapView: IMapView by lazy { childFragmentManager.findFragmentById(R.id.mapContainer) as IMapView }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myLocationButton.setOnClickListener {
            mapView.goToMyLocation()
        }
    }

    override fun withStatusBar(): Boolean {
        return false
    }

    companion object {
        fun newInstance(): GameMapFragment {
            return GameMapFragment()
        }
    }

}