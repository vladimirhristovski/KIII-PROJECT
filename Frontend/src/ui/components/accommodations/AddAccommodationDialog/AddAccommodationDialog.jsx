import React, {useState} from 'react';
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    FormControl,
    InputLabel,
    MenuItem,
    Select,
    TextField
} from "@mui/material";
import useHosts from "../../../../hooks/useHosts.js";

const initialFormData = {
    "name": "",
    "category": "",
    "hostId": "",
    "numRooms": "",
};

const AddAccommodationDialog = ({open, onClose, onAdd}) => {
    const [formData, setFormData] = useState(initialFormData);
    const {hosts} = useHosts();

    const handleChange = (event) => {
        const {name, value} = event.target;
        setFormData({...formData, [name]: value});
    };

    const handleSubmit = () => {
        onAdd(formData);
        setFormData(initialFormData);
        onClose();
    };

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>Add Accommodation</DialogTitle>
            <DialogContent>
                <TextField
                    margin="dense"
                    label="Name"
                    name="name"
                    value={formData.name}
                    onChange={handleChange}
                    fullWidth
                />
                <TextField
                    margin="dense"
                    label="Category"
                    name="category"
                    value={formData.category}
                    onChange={handleChange}
                    fullWidth
                />
                <FormControl fullWidth margin="dense">
                    <InputLabel>HostCard</InputLabel>
                    <Select
                        name="hostId"
                        value={formData.hostId}
                        onChange={handleChange}
                        label="HostCard"
                        variant="outlined">
                        {hosts.map((host) => (
                            <MenuItem key={host.id} value={host.id}>{host.name} {host.surname}</MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <FormControl fullWidth margin="dense">
                    <TextField
                        margin="dense"
                        label="Number of rooms"
                        name="numRooms"
                        type="number"
                        value={formData.numRooms}
                        onChange={handleChange}
                        fullWidth
                    />
                </FormControl>
                {/*<FormControl fullWidth margin="dense">*/}
                {/*    <InputLabel>Rented</InputLabel>*/}
                {/*    <Select*/}
                {/*        labelId="rented-label"*/}
                {/*        name="rented"*/}
                {/*        value={formData.rented}*/}
                {/*        onChange={handleChange}*/}
                {/*        label="Rented"*/}
                {/*        variant="outlined"*/}
                {/*    >*/}
                {/*        <MenuItem value={true}>Yes</MenuItem>*/}
                {/*        <MenuItem value={false}>No</MenuItem>*/}
                {/*    </Select>*/}
                {/*</FormControl>*/}
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button onClick={handleSubmit} variant="contained" color="primary">Add</Button>
            </DialogActions>
        </Dialog>
    );
};

export default AddAccommodationDialog;