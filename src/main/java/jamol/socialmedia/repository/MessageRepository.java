package jamol.socialmedia.repository;

import jamol.socialmedia.entity.Message;
import jamol.socialmedia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderOrReceiver(User sender, User receiver);

    @Query("SELECT m FROM Message m WHERE (m.sender = :user AND m.receiver = :other) OR (m.sender = :other AND m.receiver = :user) ORDER BY m.sentAt")
    List<Message> findConversation(User user, User other);
}
