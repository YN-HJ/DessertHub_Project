package com.desserthub.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.desserthub.board.Board;
import com.desserthub.board.BoardRepository;
import com.desserthub.dlike.DlikeRepository;
import com.desserthub.gallery.Gallery;
import com.desserthub.gallery.GalleryRepository;
import com.desserthub.reply.ReplyRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final GalleryRepository galleryRepository;
    private final ReplyRepository replyRepository;
    private final DlikeRepository dlikeRepository;

    public UserService(BoardRepository boardRepository, UserRepository userRepository, GalleryRepository galleryRepository, ReplyRepository replyRepository, DlikeRepository dlikeRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.galleryRepository = galleryRepository;
        this.replyRepository = replyRepository;
        this.dlikeRepository = dlikeRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUser(String userId) {
        return userRepository.findByUserId(userId);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public boolean updateUser(Long id, User userDetails) {
        boolean result = false;
        User target_user = null;
        
        try {
            target_user = userRepository.findById(id).orElseThrow(null);
        } catch (Exception e) {
            // null
        }

        if(target_user != null) {
            if (userDetails.getUserPw() != null && userDetails.getUserPw() != "") {
                target_user.setUserPw(userDetails.getUserPw());
            }
            if (userDetails.getUserEm() != null && userDetails.getUserEm() != "") {
                target_user.setUserEm(userDetails.getUserEm());
            }
            if (userDetails.getUserNn() != null && userDetails.getUserNn() != "") {
                String newUserNn = userDetails.getUserNn();
                target_user.setUserNn(newUserNn);

                List<Board> boardList = boardRepository.findByUserId(id);
                List<Gallery> galleryList = galleryRepository.findByUserId(id);
                for(Board board : boardList) {
                    board.setUserNn(newUserNn);
                }
                for(Gallery gallery : galleryList) {
                    gallery.setUserNn(newUserNn);
                }

            }

            userRepository.save(target_user);

            result = true;
        }

        return result;
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean deleteUser(String userId, String userPw) {
        User user = null;
        boolean result = false;
        try {
            user = userRepository.findByUserId(userId).orElseThrow(null);
        } catch (Exception e) {
            
        }
        Long uid = null;
        if (user != null) {
            uid = user.getId();
            if(userId.equals(user.getUserId()) && userPw.equals(user.getUserPw())) {
                deleteUser(uid);
                result = true;
            }
        }

        boardRepository.deleteAllByUserId(uid);
        galleryRepository.deleteAllByUserId(uid);
        replyRepository.deleteAllByUserId(uid);
        dlikeRepository.deleteAllByUserId(uid);

        // コメント数またはいいね数を変更 

        return result;
    }

    public boolean updateUserProfileImage(Long id, String string) {
        boolean result = false;
        User target_user = null;
        
        try {
            target_user = userRepository.findById(id).orElseThrow(null);
        } catch (Exception e) {
            // null
        }

        if(target_user != null) {
            target_user.setUserPi(string);
            userRepository.save(target_user);
            result = true;
        }

        return result;
    }

    public boolean login_check(User login_user) {
        boolean result = false;

        User user = null;

        try {
            user = userRepository.findByUserId(login_user.getUserId()).orElseThrow(null);
        } catch (Exception e) {
            //e.printStackTrace(); //NullPointerException
        }

        if(user != null) {
            if(login_user.getUserId().equals(user.getUserId())) {
                if (login_user.getUserPw().equals(user.getUserPw())) {
                    result = true;
                }
            }
        }

        return result;
    }

    public boolean id_check(String userId) {
        boolean result = false;

        if(userRepository.existsByUserId(userId)) {
            result = true;
        }

        return result;
    }

    public boolean register_check(User user) {
        boolean result = false;
        User target_user = null;
        
        try {
            target_user = userRepository.findByUserId(user.getUserId()).orElseThrow(null);
        } catch (Exception e) {
            // null
        }

        if(target_user == null) {
            target_user = new User();
            target_user.setUserId(user.getUserId());
            target_user.setUserPw(user.getUserPw());
            target_user.setUserEm(user.getUserEm());
            target_user.setUserNn(user.getUserNn());

            userRepository.save(target_user);

            result = true;
        }

        return result;
    }
    
    public User find_id(String userNn, String userEm) {
        // 入力したニックネームとメールアドレスでユーザ検索
        User user = userRepository.findByUserNnAndUserEm(userNn, userEm);

        return user;
    }

    public User find_pw(String userId, String userEm) {
        // IDとメールアドレスでユーザー検索
        User user = userRepository.findByUserIdAndUserEm(userId, userEm);

        return user;
    }
        
}
