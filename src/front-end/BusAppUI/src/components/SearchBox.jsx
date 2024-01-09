import React from 'react'
import TextField from '@mui/material/TextField'
import InputAdornment from '@mui/material/InputAdornment'
import SearchIcon from '@mui/icons-material/Search'

export const SearchBox = () => {
  return (
    <TextField
      sx={{
        width: '100%', // Adjust as needed
        px: 0,
        mb: 2,
      }}
      variant="outlined"
      placeholder="Search bus agencies"
      InputProps={{
        startAdornment: (
          <InputAdornment position="start">
            <SearchIcon />
          </InputAdornment>
        ),
      }} />
  )
}

export default SearchBox
