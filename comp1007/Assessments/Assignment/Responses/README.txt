Introduction
    DetectEdges is a photo manipulation program designed to perform horizontal
    and vertical edge-detection and smoothing operations on images. 

Usage
    'Import Image'
        • 'File'
          If the file is a PNG:
            - Name provided by user input must represent an existant file
            - Name provided by user input must include the '.png' extension
          If the file is a CSV:
            - Name provided by user input must represent an existant file
            - Name provided by user input must include the '.csv' extension
            - Elements must have values that are 0 <= x <= 255
            - All of its elements must have values that are integers
            - Image dimensions must be rectangular (i.e. n * m)
        • 'User Input'
            - All of its elements must have values that are integers

    'Import Kernel'
        • 'File'
          - File must be a '.csv'
          - Name provided by user input must represent an existant file
          - Name provided by user input must include the '.csv' extension
          - All of its elements must have values that are integers
          - Kernel dimensions must be square (i.e. k * k)
        • 'User Input'
          - All of its elements must have values that are integers

    'Convolute Image'
        • Image adhering to above requirements must be imported, also:
          - Dimensions must be greater than or equal to kernel dimensions
        • Kernel adhering to above requirements must be imported

    'Smoothen Image'
        • Image adhering to above requirements must be imported, also
          - Dimensions of smoothing surface must be odd
          - Positioning of smoothing surface on image must not exceed image
          - Smoothing factor must be between 0.0 and 1.00 (inclusive)

    'Smoothen Convoluted Image'
        • Image adhering to above requirements must be imported
        • Image must be convoluted, also:
          - Dimensions of smoothing surface must be odd
          - Positioning of smoothing surface on image must not exceed image
          - Smoothing factor must be between 0.0 and 1.00 (inclusive)

    'Export Image'
        • Image adhering to above requirements must be imported
        • Date provided by user input must represent a real calender day

    'Export Convoluted Image'
        • Image adhering to above requirements must be imported
        • Image must be convoluted
        • Date provided by user input must represent a real calender day
