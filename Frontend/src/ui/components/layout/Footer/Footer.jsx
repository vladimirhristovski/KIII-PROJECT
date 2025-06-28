import React from 'react';
import {Box, Typography, useTheme} from '@mui/material';

const Footer = () => {
    const theme = useTheme();

    return (
        <Box
            component="footer"
            sx={{
                py: 2,
                px: 2,
                mt: 'auto',
                backgroundColor: theme.palette.primary.main,
                color: theme.palette.primary.contrastText,
                textAlign: 'center',
            }}
        >
            <Typography variant="body2">
                AirBNB-Wannabe
            </Typography>
        </Box>
    );
};

export default Footer;