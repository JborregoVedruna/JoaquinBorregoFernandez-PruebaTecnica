USE caixabank;

INSERT INTO
    loan_applications (
        uuid,
        applicant_name,
        requested_amount,
        currency,
        applicant_dni,
        created_date,
        status
    )
VALUES (
        'f47ac10b-58cc-4372-a567-0e02b2c3d479',
        'Alejandro Garcia',
        15000.00,
        'EUR',
        '12345678Z',
        CURRENT_TIMESTAMP,
        0
    ),
    (
        '72e19c0a-3d2b-4f9e-8c4d-6192a5b8c3d1',
        'Maria Lopez',
        4500.50,
        'USD',
        '87654321X',
        CURRENT_TIMESTAMP,
        1
    ),
    (
        'a5b29c31-7e8d-421a-94b6-3c0f2d1e5a78',
        'Roberto Sanchez',
        60000.00,
        'EUR',
        '45678912S',
        CURRENT_TIMESTAMP,
        2
    ),
    (
        '18d9c5b2-6a3f-4e0d-82c7-5b1a9d4c3f6e',
        'Lucia Fernandez',
        1200.00,
        'GBP',
        '23456789D',
        CURRENT_TIMESTAMP,
        0
    ),
    (
        'd3e2b1a0-4c5f-4a9d-b7e8-1c2d3a4b5f6a',
        'Javier Martinez',
        35000.00,
        'JPY',
        '34567890V',
        CURRENT_TIMESTAMP,
        1
    );