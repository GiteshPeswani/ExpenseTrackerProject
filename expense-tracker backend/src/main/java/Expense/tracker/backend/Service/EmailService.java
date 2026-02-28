            package Expense.tracker.backend.Service;

            import Expense.tracker.backend.Repository.AuthRepository;
            import jakarta.mail.MessagingException;
            import jakarta.mail.internet.MimeMessage;
            import org.springframework.beans.factory.annotation.Autowired;
            import org.springframework.mail.javamail.JavaMailSender;
            import org.springframework.mail.javamail.MimeMessageHelper;
            import org.springframework.stereotype.Service;

            @Service
            public class EmailService {
                @Autowired
                JavaMailSender javaMailSender;
                @Autowired
                AuthRepository  authRepository;
                public void SendEmail(String to,String token){
                    String resetLink="http://localhost:4200/reset-password/"+token;
                    MimeMessage message=javaMailSender.createMimeMessage();
                            //Mime:multimedia there is also simple mail message this will help to send html
                    //message is kisko kya bhejna hai
                   try{
                       MimeMessageHelper messageHelper=new MimeMessageHelper(message,true,"UTF-8");
                       messageHelper.setTo(to);
                       messageHelper.setSubject("reset password-expense tracker");
                       messageHelper.setText(buildhtml(resetLink),true);
                       javaMailSender.send(message);
                   }
                   catch (MessagingException e) {
                       System.out.println("Email sending failed: " + e.getMessage());
                   }

                    //ek message banaya aur message ka wrapper aur message ko token bheja uska subject bheja aur jisko bhejna hai bhej diya


                }
                private String buildhtml(String resetLink){
                    return "<html><body>" +
                            "<h3>Reset Your Password</h3>" +
                            "<p>Click the link below to reset your password:</p>" +
                            "<a href=\"" + resetLink + "\">Reset Password</a>" +
                            "<p>This link will expire in 5 minutes.</p>" +
                            "</body></html>";

                }

        //        public void sendPasswordEmail(String toEmail,String resetLink){
        //
        //        }


            }
