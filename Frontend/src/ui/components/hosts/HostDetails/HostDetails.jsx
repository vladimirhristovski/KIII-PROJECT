import React from 'react';
import {useNavigate, useParams} from "react-router";
import useHostDetails from "../../../../hooks/useHostDetails.js";
import {Box, Breadcrumbs, Button, Chip, CircularProgress, Grid, Link, Paper, Stack, Typography} from "@mui/material";
import {ArrowBack, Category} from "@mui/icons-material";

const HostDetails = () => {
    const navigate = useNavigate();
    const {id} = useParams();
    const {host, country} = useHostDetails(id);

    if (!host || !country) {
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
                        navigate("/hosts");
                    }}
                >
                    Hosts
                </Link>
                <Typography color="text.primary">{host.name}</Typography>
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
                                {host.name} {host.surname}
                            </Typography>

                            <Stack direction="row" spacing={1} sx={{mb: 3}}>
                                <Chip
                                    icon={<Category/>}
                                    label={country.name}
                                    color="primary"
                                    variant="outlined"
                                    sx={{p: 2}}
                                />
                            </Stack>
                        </Box>
                    </Grid>
                    <Grid size={12} display="flex" justifyContent="space-between">
                        <Button
                            variant="outlined"
                            startIcon={<ArrowBack/>}
                            onClick={() => navigate("/hosts")}
                        >
                            Back to Hosts
                        </Button>
                    </Grid>
                </Grid>
            </Paper>
        </Box>
    );
};

export default HostDetails;