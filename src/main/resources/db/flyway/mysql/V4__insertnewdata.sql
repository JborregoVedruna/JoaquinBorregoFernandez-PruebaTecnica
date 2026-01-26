INSERT INTO
    users (
        user_uuid,
        username,
        password,
        user_dni,
        rol,
        account_expiration_date,
        is_locked,
        credentials_expiration_date,
        is_enabled
    )
VALUES (
        'f47ac10b-58cc-4372-a567-0e02b2c3d123',
        'alejandro_garcia',
        '$2a$10$OMcl/yAWvJdJkHBKkSGZ6OyINZDZ/XOB3Kp2uwWmjwoe/BCbDouV.',
        '12345678Z',
        0,
        DATE_ADD(
            CURRENT_TIMESTAMP,
            INTERVAL 1 YEAR
        ),
        0,
        DATE_ADD(
            CURRENT_TIMESTAMP,
            INTERVAL 1 YEAR
        ),
        1
    ),
    (
        'f47ac10b-58cc-4372-a567-0e02b2c3d124',
        'maria_lopez',
        '$2a$10$OMcl/yAWvJdJkHBKkSGZ6OyINZDZ/XOB3Kp2uwWmjwoe/BCbDouV.',
        '87654321X',
        0,
        DATE_ADD(
            CURRENT_TIMESTAMP,
            INTERVAL 1 YEAR
        ),
        0,
        DATE_ADD(
            CURRENT_TIMESTAMP,
            INTERVAL 1 YEAR
        ),
        1
    ),
    (
        'f47ac10b-58cc-4372-a567-0e02b2c3d125',
        'roberto_sanchez',
        '$2a$10$OMcl/yAWvJdJkHBKkSGZ6OyINZDZ/XOB3Kp2uwWmjwoe/BCbDouV.',
        '45678912S',
        0,
        DATE_ADD(
            CURRENT_TIMESTAMP,
            INTERVAL 1 YEAR
        ),
        0,
        DATE_ADD(
            CURRENT_TIMESTAMP,
            INTERVAL 1 YEAR
        ),
        1
    ),
    (
        'f47ac10b-58cc-4372-a567-0e02b2c3d126',
        'lucia_fernandez',
        '$2a$10$OMcl/yAWvJdJkHBKkSGZ6OyINZDZ/XOB3Kp2uwWmjwoe/BCbDouV.',
        '23456789D',
        0,
        DATE_ADD(
            CURRENT_TIMESTAMP,
            INTERVAL 1 YEAR
        ),
        0,
        DATE_ADD(
            CURRENT_TIMESTAMP,
            INTERVAL 1 YEAR
        ),
        1
    ),
    (
        'f47ac10b-58cc-4372-a567-0e02b2c3d127',
        'javier_martinez',
        '$2a$10$OMcl/yAWvJdJkHBKkSGZ6OyINZDZ/XOB3Kp2uwWmjwoe/BCbDouV.',
        '34567890V',
        0,
        DATE_ADD(
            CURRENT_TIMESTAMP,
            INTERVAL 1 YEAR
        ),
        0,
        DATE_ADD(
            CURRENT_TIMESTAMP,
            INTERVAL 1 YEAR
        ),
        1
    ),
    (
        'f47ac10b-58cc-4372-a567-0e02b2c3d128',
        'manager',
        '$2a$10$OMcl/yAWvJdJkHBKkSGZ6OyINZDZ/XOB3Kp2uwWmjwoe/BCbDouV.',
        '11111111H',
        1,
        DATE_ADD(
            CURRENT_TIMESTAMP,
            INTERVAL 1 YEAR
        ),
        0,
        DATE_ADD(
            CURRENT_TIMESTAMP,
            INTERVAL 1 YEAR
        ),
        1
    ),
    (
        'f47ac10b-58cc-4372-a567-0e02b2c3d129',
        'admin',
        '$2a$10$OMcl/yAWvJdJkHBKkSGZ6OyINZDZ/XOB3Kp2uwWmjwoe/BCbDouV.',
        '22222222J',
        2,
        DATE_ADD(
            CURRENT_TIMESTAMP,
            INTERVAL 1 YEAR
        ),
        0,
        DATE_ADD(
            CURRENT_TIMESTAMP,
            INTERVAL 1 YEAR
        ),
        1
    );

INSERT INTO
    loan_applications (
        uuid,
        requested_amount,
        currency,
        created_date,
        status,
        users_user_uuid
    )
VALUES (
        'f47ac10b-58cc-4372-a567-0e02b2c3d479',
        15000.00,
        'EUR',
        CURRENT_TIMESTAMP,
        0,
        'f47ac10b-58cc-4372-a567-0e02b2c3d123'
    ),
    (
        '72e19c0a-3d2b-4f9e-8c4d-6192a5b8c3d1',
        4500.50,
        'USD',
        CURRENT_TIMESTAMP,
        1,
        'f47ac10b-58cc-4372-a567-0e02b2c3d124'
    ),
    (
        'a5b29c31-7e8d-421a-94b6-3c0f2d1e5a78',
        60000.00,
        'EUR',
        CURRENT_TIMESTAMP,
        2,
        'f47ac10b-58cc-4372-a567-0e02b2c3d125'
    ),
    (
        '18d9c5b2-6a3f-4e0d-82c7-5b1a9d4c3f6e',
        1200.00,
        'GBP',
        CURRENT_TIMESTAMP,
        0,
        'f47ac10b-58cc-4372-a567-0e02b2c3d126'
    ),
    (
        'd3e2b1a0-4c5f-4a9d-b7e8-1c2d3a4b5f6a',
        35000.00,
        'JPY',
        CURRENT_TIMESTAMP,
        1,
        'f47ac10b-58cc-4372-a567-0e02b2c3d127'
    );