import React, {useState} from 'react';
import {Box, Button, CircularProgress} from "@mui/material";
import AccommodationsGrid from "../../components/accommodations/AccommodationsGrid/AccommodationsGrid.jsx";
import useAccommodations from "../../../hooks/useAccommodations.js";
import "./AccommodationsPage.css";
import AddAccommodationDialog from "../../components/accommodations/AddAccommodationDialog/AddAccommodationDialog.jsx";
import { FormControl, InputLabel, Select, MenuItem } from "@mui/material";


const AccommodationsPage = () => {
    const {accommodations, loading, onAdd, onEdit, onDelete} = useAccommodations();
    const [addAccommodationDialogOpen, setAddAccommodationDialogOpen] = useState(false);
    const [selectedCategory, setSelectedCategory] = useState('');

    const filteredAccommodations = selectedCategory
        ? accommodations.filter(acc => acc.category === selectedCategory)
        : accommodations;


    return (
        <>
            <Box className="products-box">
                {loading && (
                    <Box className="progress-box">
                        <CircularProgress/>
                    </Box>
                )}
                {!loading &&
                    <>
                        <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", mb: 2 }}>
                            <FormControl sx={{ minWidth: 200 }}>
                                <InputLabel id="category-select-label">Category</InputLabel>
                                <Select
                                    labelId="category-select-label"
                                    id="category-select"
                                    value={selectedCategory}
                                    label="Category"
                                    onChange={(e) => setSelectedCategory(e.target.value)}
                                >
                                    <MenuItem value="">All</MenuItem>
                                    <MenuItem value="HOTEL">HOTEL</MenuItem>
                                    <MenuItem value="MOTEL">MOTEL</MenuItem>
                                    <MenuItem value="FLAT">FLAT</MenuItem>
                                    {/* Add more categories here if you want */}
                                </Select>
                            </FormControl>

                            <Button variant="contained" color="primary" onClick={() => setAddAccommodationDialogOpen(true)}>
                                Add Accommodation
                            </Button>
                        </Box>

                        <AccommodationsGrid accommodations={filteredAccommodations} onEdit={onEdit} onDelete={onDelete}/>
                    </>}
            </Box>
            <AddAccommodationDialog
                open={addAccommodationDialogOpen}
                onClose={() => setAddAccommodationDialogOpen(false)}
                onAdd={onAdd}
            />
        </>
    );
};

export default AccommodationsPage;