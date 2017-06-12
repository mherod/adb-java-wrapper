package co.herod.adbwrapper;

import io.reactivex.Completable;
import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("WeakerAccess")
class ProcessHelper {

    static Observable<String> observableProcess(@NotNull final ProcessBuilder processBuilder) {

        return Observable.just(processBuilder)
                .map(ProcessBuilder::start)
                .flatMap(ProcessHelper::observableProcess)
                .map(String::trim)
                .doOnEach(AdbBusManager.ADB_BUS);
    }

    private static Observable<String> observableProcess(@NotNull final Process process) {

        return Observable.fromPublisher(s -> {

            String line;

            final InputStream inputStream = process.getInputStream();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                while ((line = br.readLine()) != null) {
                    s.onNext(line);
                }
            } catch (final IOException e) {
                s.onError(e);
            }
            s.onComplete();
        });
    }

    static InputStream processOutput(@NotNull final Process process) {
        return process.getInputStream();
    }

    @SuppressWarnings("SameParameterValue")
    static void blocking(final ProcessBuilder processBuilder, final int timeout, final TimeUnit timeUnit) {

        Completable.fromObservable(observableProcess(processBuilder))
                .blockingAwait(timeout, timeUnit);
    }

    static void blocking(final ProcessBuilder processBuilder) {
        blocking(processBuilder, 10, TimeUnit.SECONDS);
    }
}