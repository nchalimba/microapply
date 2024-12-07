package de.abubeker.microapply.notification.constants;

public class EmailConstants {
    public static final String APPLICATION_CREATED_EMAIL_TEMPLATE = """
        <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: auto; background: white; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); padding: 20px;">
                    <h1 style="color: #333;">🎉 Application Submitted Successfully!</h1>
                    <p>Dear <strong>%s</strong>,</p>
                    <p style="font-size: 16px; line-height: 1.5;">
                        We are excited to inform you that your application has been submitted successfully!
                    </p>
                    <p style="font-size: 16px; line-height: 1.5;">
                        Thank you for taking the time to apply. Our team will review your application and get back to you soon.
                    </p>
                    <p style="font-size: 16px; line-height: 1.5;">
                        If you have any questions, feel free to reach out to us.
                    </p>
                    <p>Best Regards,</p>
                    <p>The Team</p>
                </div>
            </body>
        </html>
    """;
}
