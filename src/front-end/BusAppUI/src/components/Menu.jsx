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

const Menu = () => {
  const [open, setOpen] = React.useState(false)

  const toggleDrawer = (open) => {
    setOpen(open)
  }

  const ANCHOR = 'bottom'
  const DRAWER_BLEEDING = 31
  const ICON_WIDTH = '16px';
  const DRAWER_HEIGHT = '50%'

  const theme = useTheme()

  return (
    <>
      <Drawer
        sx={{ 
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
            backgroundColor: 'white',
            position: 'absolute',
            top: -DRAWER_BLEEDING,
            visibility: 'visible',
            borderRadius: `${theme.spacing(1)} ${theme.spacing(1)} 0 0`,
            right: 0,
            left: 0,
            // boxShadow: '0px 3px 5px 2px rgba(0, 0, 0, 0.3)',
          }}
        >
          <DragHandleIcon
            sx={{
            position: 'relative', left: `calc(50% - ${ICON_WIDTH} / 2)`,
            }}
          >
          </DragHandleIcon>
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