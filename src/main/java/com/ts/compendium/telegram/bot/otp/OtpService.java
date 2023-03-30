package com.ts.compendium.telegram.bot.otp;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.ts.compendium.telegram.bot.exceptions.NoOtpForChatException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;

    public void sendOtp(String text) {
        String otp = randomCode4Digits();
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setOtp(otp);
        otpEntity.setPhone(text);
        otpEntity.setLastUpdateDate(LocalDateTime.now());
        otpRepository.save(otpEntity);

    }

    public void sendOtp(String text, Long chatId) {
        String otp = randomCode4Digits();
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setOtp(otp);
        otpEntity.setPhone(text);
        otpEntity.setLastUpdateDate(LocalDateTime.now());
        otpEntity.setChatId(chatId);
        otpRepository.save(otpEntity);

    }

    public boolean isOtpValid(String userNumber, String text) {
        List<OtpEntity> otpEntities = otpRepository.find(userNumber, text);
        for (OtpEntity otpEntity : otpEntities) {
            if (otpEntity.getOtp().equals(text)
                    && Duration.between(otpEntity.getLastUpdateDate(), LocalDateTime.now()).toHours() < 3) {
                return true;
            }
        }
        return false;
    }

    public String getNumberByChatId(Long chatId) {
        List<OtpEntity> otpEntities = otpRepository.findLastByChatId(chatId);
        for (OtpEntity otpEntity : otpEntities) {
            if (Duration.between(otpEntity.getLastUpdateDate(), LocalDateTime.now()).toHours() < 3) {
                return otpEntity.getPhone();
            }
        }
        throw new NoOtpForChatException("Can't find OTP for chatId = " + chatId);
    }

    public static String randomCode4Digits() {
        return "0000";//String.format("%04d", ThreadLocalRandom.current().nextInt(0, 9999));
    }
}
