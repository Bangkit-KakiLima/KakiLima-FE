import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ping.locations.LocationResponse
import com.dicoding.ping.locations.LocationsRepository
import kotlinx.coroutines.launch

class LocationsModel(private val repository: LocationsRepository) : ViewModel() {

    private val _locations = MutableLiveData<List<LocationResponse>>()
    val locations: LiveData<List<LocationResponse>> get() = _locations

    fun getAllLocations() {
        viewModelScope.launch {
            try {
                val result = repository.getAllLocations()
                _locations.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
