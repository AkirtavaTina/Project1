import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ButtonsViewModel : ViewModel() {

    private val _buttonStates = MutableLiveData<Map<Int, Boolean>>()
    val buttonStates: LiveData<Map<Int, Boolean>> get() = _buttonStates

    init {
        _buttonStates.value = emptyMap()
    }

    fun setButtonState(buttonId: Int, isClicked: Boolean) {
        val updatedStates = _buttonStates.value.orEmpty().toMutableMap()
        updatedStates[buttonId] = isClicked
        _buttonStates.value = updatedStates

    }
}
