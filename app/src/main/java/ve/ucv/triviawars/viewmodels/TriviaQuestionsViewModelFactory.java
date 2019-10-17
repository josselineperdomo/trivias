package ve.ucv.triviawars.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TriviaQuestionsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Application application;
    private final int triviaId;

    public TriviaQuestionsViewModelFactory(@NonNull Application application, int triviaId) {
        this.application = application;
        this.triviaId = triviaId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new TriviaQuestionsViewModel(application, triviaId);
    }
}
