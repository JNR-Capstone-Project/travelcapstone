package com.codeup.travelcapstone.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {
    // Find your Account Sid and Token at twilio.com/user/account
    @Value("${twilio.sid}")
    private String ACCOUNT_SID;
    @Value("${twilio.auth}")
    private String AUTH_TOKEN;
    @Value("${twilio.from}")
    private String number;

    public String sendText(String content, String to) {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber("+1" + to),
                new PhoneNumber(number), content).create();

        return message.getSid();
    }
}