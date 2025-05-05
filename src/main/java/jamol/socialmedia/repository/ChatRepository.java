package jamol.socialmedia.repository;

import jamol.socialmedia.entity.Chat;
import jamol.socialmedia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    // Foydalanuvchi tomonidan yuborilgan yoki olingan xabarlarni olish
    List<Chat> findBySenderOrReceiver(User sender, User receiver);

    // Ikkala foydalanuvchi o'rtasidagi xabarlarni olish
    List<Chat> findBySenderAndReceiver(User sender, User receiver);
}
