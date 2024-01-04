//react
import { useState } from 'react'

//MUI
import { Box } from '@mui/material'

//components
import SideMenu from './components/SideMenu'


function App() {
  return (
    <Box sx={{ display: 'flex' }}>
      <SideMenu/>
    </Box>
  )
}

export default App
