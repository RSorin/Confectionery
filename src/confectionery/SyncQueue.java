/*
 * Radu Sorin-Gabriel
 * Grupa 410 - M1
 */

package confectionery;

import java.util.Optional;

public interface SyncQueue<T> {
    /**
     * Puts a message in the queue
     * @return whether the operation succeeded (queue open)
     */
    boolean put(T message) throws InterruptedException;
    /**
     * Takes a message from the queue
     * @return an optional message, if queue open
     */
    Optional<T> take() throws InterruptedException;
    /**
     * Closes the queue, making subsequent calls invalid
     */
    void close();
}