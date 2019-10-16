package ve.ucv.triviawars.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TriviaDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Application application;
    private final int triviaId;

    public TriviaDetailViewModelFactory(@NonNull Application application, int triviaId) {
        this.application = application;
        this.triviaId = triviaId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new TriviaDetailViewModel(application, triviaId);
    }
}
