import React from 'react';
import {useNavigate, useParams} from "react-router";
import useAccommodationDetails from "../../../../hooks/useAccommodationDetails.js";
import {Box, Breadcrumbs, Button, Chip, CircularProgress, Grid, Link, Paper, Stack, Typography} from "@mui/material";
import {ArrowBack, Category, FavoriteBorder, Share, ShoppingCart} from "@mui/icons-material";

const AccommodationDetails = () => {
    const navigate = useNavigate();
    const {id} = useParams();
    const {accommodation, host} = useAccommodationDetails(id);

    if (!accommodation || !host) {
        return (
            <Box sx={{display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '60vh'}}>
                <CircularProgress/>
            </Box>
        );
    }

    return (
        <Box>
            <Breadcrumbs aria-label="breadcrumb" sx={{mb: 3}}>
                <Link
                    underline="hover"
                    color="inherit"
                    href="#"
                    onClick={(e) => {
                        e.preventDefault();
                        navigate("/accommodations");
                    }}
                >
                    Accommodations
                </Link>
                <Typography color="text.primary">{accommodation.name}</Typography>
            </Breadcrumbs>

            <Paper elevation={2} sx={{p: 4, borderRadius: 4}}>
                <Grid container spacing={4}>
                    <Grid size={{xs: 12, md: 3}}>
                        <Box sx={{
                            display: 'flex',
                            justifyContent: 'center',
                            mb: 4,
                            bgcolor: 'background.paper',
                            p: 3,
                            borderRadius: 3,
                            boxShadow: 1
                        }}>
                        </Box>
                    </Grid>
                    <Grid size={{xs: 12, md: 9}}>
                        <Box sx={{mb: 3}}>
                            <Typography variant="h3" gutterBottom sx={{fontWeight: 600}}>
                                {accommodation.name}
                            </Typography>

                            <Typography variant="h4" color="primary.main" sx={{mb: 3}}>
                                Category: {accommodation.category}
                            </Typography>

                            <Typography variant="subtitle1" sx={{mb: 3}}>
                                {accommodation.numRooms} total rooms
                            </Typography>

                            <Typography variant="subtitle1" sx={{mb: 3}}>
                                Rented: {accommodation.rented ? 'Yes' : 'No'}
                            </Typography>

                            <Stack direction="row" spacing={1} sx={{mb: 3}}>
                                <Chip
                                    icon={<Category/>}
                                    label={host.name}
                                    color="primary"
                                    variant="outlined"
                                    sx={{p: 2}}
                                />
                            </Stack>
                        </Box>
                    </Grid>
                    <Grid size={12} display="flex" justifyContent="space-between">
                        <Stack direction="row" spacing={2}>
                            <Button
                                variant="contained"
                                color="primary"
                                startIcon={<ShoppingCart/>}
                                size="large"
                            >
                                Add to Temporary Reservations
                            </Button>
                        </Stack>
                        <Button
                            variant="outlined"
                            startIcon={<ArrowBack/>}
                            onClick={() => navigate("/accommodations")}
                        >
                            Back to Accommodations
                        </Button>
                    </Grid>
                </Grid>
            </Paper>
        </Box>
    );
};

export default AccommodationDetails;