//React
import React, { useEffect } from 'react'

//MUI
import { styled } from '@mui/material/styles'
import { grey } from '@mui/material/colors'
import Box from '@mui/material/Box'
import Drawer from '@mui/material/SwipeableDrawer'
import Skeleton from '@mui/material/Skeleton'
import DragHandleIcon from '@mui/icons-material/DragHandle'
import List from '@mui/material/List'
import ListItem from '@mui/material/ListItem'
import ListItemText from '@mui/material/ListItemText'
import { useTheme } from '@emotion/react'
import TextField from '@mui/material/TextField'
import InputAdornment from '@mui/material/InputAdornment'
import SearchIcon from '@mui/icons-material/Search'

const Menu = () => {
  const [open, setOpen] = React.useState(true)

  const toggleDrawer = (open) => {
    setOpen(open)
  }

  const ANCHOR = 'bottom'
  const DRAWER_BLEEDING = 103
  const ICON_WIDTH = '16px';
  const DRAWER_HEIGHT = '50%'

  const theme = useTheme()

  return (
    <>
      <Drawer
        sx={{ 
          backgroundColor: 'white',
          '.MuiPaper-root': {
            // borderRadius: `${theme.spacing(1)} ${theme.spacing(1)} 0 0`,
            height: `calc(${DRAWER_HEIGHT} - ${DRAWER_BLEEDING}px)`,
            overflow: 'visible'
          }
        }}
        swipeAreaWidth={DRAWER_BLEEDING}
        anchor={ANCHOR}
        open={open}
        onClose={() => {toggleDrawer(false)}}
        onOpen={() => {toggleDrawer(true)}}
      >
        <Box
          sx={{
            border: 'none',
            backgroundColor: 'white',
            position: 'absolute',
            top: -DRAWER_BLEEDING,
            visibility: 'visible',
            borderRadius: `${theme.spacing(1)} ${theme.spacing(1)} 0 0`,
            right: 0,
            left: 0,
            elevation: 0,
          }}
        >
          <DragHandleIcon
            sx={{
            position: 'relative', left: `calc(50% - ${ICON_WIDTH} / 2)`,
            }}
          >
          </DragHandleIcon>
          <TextField
            sx={{
              mt: 0, // Add some top margin to separate it from the DragHandleIcon
              mb: 2, // Add some bottom margin to separate it from the DragHandleIcon
              width: '90%', // Adjust as needed
              marginLeft: '5%', // Center the TextField
              marginRight: '5%', // Center the TextField
            }}
            variant="outlined"
            placeholder="Search"
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <SearchIcon />
                </InputAdornment>
              ),
            }}
        />
        </Box>
        <Box
          sx={{
            px: 2,
            pb: 2,
            height: '100%',
            overflow: 'auto',
          }}
        >
          <Skeleton variant="rectangular" height="100%"/>
        </Box>
      </Drawer>
    </>
  )
}

export default Menu